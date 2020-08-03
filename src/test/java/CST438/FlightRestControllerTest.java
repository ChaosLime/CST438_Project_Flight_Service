package CST438;


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import CST438.controller.FlightRestController;
import CST438.domain.Flight;
import CST438.domain.FlightRepository;
import CST438.domain.FlightSeatInfo;
import CST438.service.FlightService;
import static org.mockito.BDDMockito.given;         
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;  

import java.util.ArrayList;
import java.util.List;

//class must be annotated as WebMvcTest,  not SpringBootTest
@WebMvcTest(FlightRestController.class)
public class FlightRestControllerTest {

  // declare as @MockBean those classes which will be stubbed in the test
  // These classes must be Spring components (such as Repositories)
  // or @Service classes.
  
  @MockBean
  private FlightService flightService;
  
  @MockBean
  private FlightRepository flightRepository;
  
  // This class is used for make simulated HTTP requests to the class
  // being tested.
  @Autowired
  private MockMvc mvc;
  
  // These objects will be magically initialized by the initFields method below.
  private JacksonTester<List<Flight>> jsonFlightListAttempt;

  // This method is executed before each Test.
  @BeforeEach
  public void setUpEach() {
      MockitoAnnotations.initMocks(this);
      JacksonTester.initFields(this, new ObjectMapper());
  }
  
  @Test
  public void test() throws Exception {
    Flight flight1 = new Flight(1, "abc", "LAX", "10:00 AM", "SMX", "1:00 PM", "07/30/2020", new FlightSeatInfo("1", 10, "econ", (double) 99.99));
    Flight flight2 = new Flight(2, "def", "NYC", "9:30 PM", "LAX", "5:00 AM", "08/2/2020", new FlightSeatInfo("2", 1, "lux", (double) 465.95));
    
    List<Flight> flightList = new ArrayList<Flight>();
    flightList.add(flight1);
    flightList.add(flight2);
    
    assertThat(flightList).isNotEmpty();
    
    given(flightService.getFlightList("08-18-2020", "Denver", "Los Angeles")).willReturn(flightList);
    
    List<Flight> flightListResult = flightService.getFlightList("08-18-2020", "Denver", "Los Angeles");
    
    assertThat(flightListResult).isEqualTo(flightList);
    
    MockHttpServletResponse response = mvc.perform(get("/api/08-18-2020/Denver/Los Angeles"))
            .andReturn().getResponse();
    
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    
    List<Flight> flightListResult2 = jsonFlightListAttempt.parseObject(response.getContentAsString());
    
    assertThat(flightListResult2).isEqualTo(flightList);
  }
}
