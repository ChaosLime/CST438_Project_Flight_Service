package CST438.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import CST438.domain.Flight;
import CST438.domain.FlightInfo;
import CST438.domain.FlightSeatInfo;
import CST438.repository.FlightRepository;
import CST438.repository.FlightSeatInfoRepository;

@SpringBootTest
public class FlightSeatInfoServiceTest {

  @Autowired
  FlightSeatInfoService flightSeatInfoService;

  @MockBean
  private FlightSeatInfoRepository seatRepo;

  @MockBean
  private FlightRepository flightRepo;

  @Test
  public void contextLoads() {

  }

  @Test
  public void testGetFlight() {
    FlightSeatInfo seatInfo = new FlightSeatInfo(23, 95, "economy", 945.23);

    given(seatRepo.findById(seatInfo.getId())).willReturn(seatInfo);

    Flight flight = new Flight(seatInfo.getFlightNumber(), "Test Airline", "Depart Airport",
        "3:00 AM", "Arrival Airport", "5:30 PM", "8-29-2020");

    given(flightRepo.findByFlightNumber(seatInfo.getFlightNumber())).willReturn(flight);

    FlightInfo testData = flightSeatInfoService.getFlight(seatInfo.getId());
    seatInfo.setSeatsAvailable(seatInfo.getSeatsAvailable() - 1);
    FlightInfo expectedData = new FlightInfo(flight, seatInfo);

    assertEquals(expectedData, testData);
  }

}
