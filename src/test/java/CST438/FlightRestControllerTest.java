package CST438;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.ArrayList;
import java.util.List;
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
import CST438.domain.FlightInfo;
import CST438.domain.FlightSeatInfo;
import CST438.domain.Reservation;
import CST438.repository.FlightRepository;
import CST438.repository.FlightSeatInfoRepository;
import CST438.repository.ReservationRepository;
import CST438.service.FlightSeatInfoService;
import CST438.service.FlightService;
import CST438.service.ReservationService;

// class must be annotated as WebMvcTest, not SpringBootTest
@WebMvcTest(FlightRestController.class)
public class FlightRestControllerTest {

  // declare as @MockBean those classes which will be stubbed in the test
  // These classes must be Spring components (such as Repositories)
  // or @Service classes.

  @MockBean
  private FlightService flightService;

  @MockBean
  private ReservationService reservationService;

  @MockBean
  private ReservationRepository reservationRepository;

  @MockBean
  private FlightSeatInfoService flightSeatInfoService;

  @MockBean
  private FlightRepository flightRepository;

  @MockBean
  private FlightSeatInfoRepository flightSeatInfoRepository;

  // This class is used for make simulated HTTP requests to the class
  // being tested.
  @Autowired
  private MockMvc mvc;

  // These objects will be magically initialized by the initFields method below.
  private JacksonTester<List<FlightInfo>> jsonFlightListAttempt;

  // This method is executed before each Test.
  @BeforeEach
  public void setUpEach() {
    MockitoAnnotations.initMocks(this);
    JacksonTester.initFields(this, new ObjectMapper());
  }

  /**
   * This tests the FlightSearchAPI.
   * 
   * @throws Exception
   */
  @Test
  public void flightSearchApiTest() throws Exception {
    Flight flight1 = new Flight(1, "abc", "LAX", "10:00 AM", "SMX", "1:00 PM", "07/30/2020");
    Flight flight2 = new Flight(2, "def", "NYC", "9:30 PM", "LAX", "5:00 AM", "08/2/2020");

    FlightInfo flightInfo1 = new FlightInfo(flight1, new FlightSeatInfo(1, 10, "econ", 99.99));
    FlightInfo flightInfo2 = new FlightInfo(flight2, new FlightSeatInfo(2, 1, "lux", 465.95));

    List<FlightInfo> flightInfoList = new ArrayList<FlightInfo>();

    flightInfoList.add(flightInfo1);
    flightInfoList.add(flightInfo2);

    given(flightService.getFlightAndSeatInfo("8/18/2020", "Denver", "Los Angeles"))
        .willReturn(flightInfoList);

    MockHttpServletResponse response = mvc
        .perform(get("/api/FlightDate/08-18-2020/DepartureCity/Denver/ArrivalCity/Los Angeles"))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    List<FlightInfo> flightInfoListResultFromApi = jsonFlightListAttempt
        .parseObject(response.getContentAsString());

    assertThat(flightInfoListResultFromApi).isEqualTo(flightInfoList);
  }

  @Test
  public void createBookingApiTest() throws Exception {
    Flight flight1 = new Flight(1, "abc", "LAX", "10:00 AM", "SMX", "1:00 PM", "07/30/2020");
    Flight flight2 = new Flight(2, "def", "NYC", "9:30 PM", "LAX", "5:00 AM", "08/2/2020");

    FlightInfo flightInfo1 = new FlightInfo(flight1, new FlightSeatInfo(1, 10, "econ", 99.99));
    FlightInfo flightInfo2 = new FlightInfo(flight2, new FlightSeatInfo(2, 1, "lux", 465.95));

    given(flightSeatInfoService.onlyGetFlight(1)).willReturn(flightInfo1);
    given(flightSeatInfoService.onlyGetFlight(2)).willReturn(flightInfo2);

    given(flightSeatInfoService.getFlight(flight1.getFlightNumber())).willReturn(flightInfo1);
    given(flightSeatInfoService.getFlight(flight2.getFlightNumber())).willReturn(flightInfo2);

    Reservation booking = new Reservation(null, flight1.getFlightNumber(),
        flight2.getFlightNumber(), false);
    long bookingId = 0l;
    booking.setBookId(bookingId);

    given(reservationService.bookFlight(booking)).willReturn(true);

    MockHttpServletResponse response = mvc.perform(get("/api/SeatID1/1/SeatID2/2")).andReturn()
        .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    String retrievedBookingId = response.getContentAsString();

    assertThat(retrievedBookingId).isEqualTo((bookingId + "")); // + "" makes the long a
    // string
  }

  @Test
  public void viewBookingApiTest() throws Exception {
    Flight flight1 = new Flight(1, "abc", "LAX", "10:00 AM", "SMX", "1:00 PM", "07/30/2020");
    Flight flight2 = new Flight(2, "def", "NYC", "9:30 PM", "LAX", "5:00 AM", "08/2/2020");

    FlightSeatInfo flightSeatInfo1 = new FlightSeatInfo(1, 10, "econ", 99.99);
    FlightSeatInfo flightSeatInfo2 = new FlightSeatInfo(2, 1, "lux", 465.95);

    FlightInfo flightInfo1 = new FlightInfo(flight1, flightSeatInfo1);
    FlightInfo flightInfo2 = new FlightInfo(flight2, flightSeatInfo2);

    List<FlightInfo> flightInfoList = new ArrayList<FlightInfo>();

    flightInfoList.add(flightInfo1);
    flightInfoList.add(flightInfo2);

    given(flightSeatInfoService.onlyGetFlight(1)).willReturn(flightInfo1);
    given(flightSeatInfoService.onlyGetFlight(2)).willReturn(flightInfo2);

    Reservation reservation = new Reservation(null, 1, 2, false);

    given(reservationService.getBooking(0)).willReturn(reservation);

    MockHttpServletResponse response = mvc.perform(get("/api/BookingID/0")).andReturn()
        .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    List<FlightInfo> flightInfoListResultFromApi = jsonFlightListAttempt
        .parseObject(response.getContentAsString());

    assertThat(flightInfoListResultFromApi).isEqualTo(flightInfoList);
  }

  @Test
  public void cancelBookingApiTest() throws Exception {
    Flight flight1 = new Flight(1, "abc", "LAX", "10:00 AM", "SMX", "1:00 PM", "07/30/2020");
    Flight flight2 = new Flight(2, "def", "NYC", "9:30 PM", "LAX", "5:00 AM", "08/2/2020");

    FlightSeatInfo flightSeatInfo1 = new FlightSeatInfo(1, 10, "econ", 99.99);
    FlightSeatInfo flightSeatInfo2 = new FlightSeatInfo(2, 1, "lux", 465.95);

    FlightInfo flightInfo1 = new FlightInfo(flight1, flightSeatInfo1);
    FlightInfo flightInfo2 = new FlightInfo(flight2, flightSeatInfo2);

    List<FlightInfo> flightInfoList = new ArrayList<FlightInfo>();

    flightInfoList.add(flightInfo1);
    flightInfoList.add(flightInfo2);

    given(flightSeatInfoService.onlyGetFlight(1)).willReturn(flightInfo1);
    given(flightSeatInfoService.onlyGetFlight(2)).willReturn(flightInfo2);

    given(reservationService.cancelBooking(0)).willReturn(true);

    Reservation reservation = new Reservation(null, 1, 2, false);

    given(reservationService.getBooking(0)).willReturn(reservation);

    MockHttpServletResponse response = mvc.perform(get("/api/Cancel/BookingID/0")).andReturn()
        .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    response = mvc.perform(get("/api/Cancel/BookingID/1")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  public void cancellationStatusApiTest() throws Exception {
    Flight flight1 = new Flight(1, "abc", "LAX", "10:00 AM", "SMX", "1:00 PM", "07/30/2020");
    Flight flight2 = new Flight(2, "def", "NYC", "9:30 PM", "LAX", "5:00 AM", "08/2/2020");

    FlightSeatInfo flightSeatInfo1 = new FlightSeatInfo(1, 10, "econ", 99.99);
    FlightSeatInfo flightSeatInfo2 = new FlightSeatInfo(2, 1, "lux", 465.95);

    FlightInfo flightInfo1 = new FlightInfo(flight1, flightSeatInfo1);
    FlightInfo flightInfo2 = new FlightInfo(flight2, flightSeatInfo2);

    List<FlightInfo> flightInfoList = new ArrayList<FlightInfo>();

    flightInfoList.add(flightInfo1);
    flightInfoList.add(flightInfo2);

    given(flightSeatInfoService.onlyGetFlight(1)).willReturn(flightInfo1);
    given(flightSeatInfoService.onlyGetFlight(2)).willReturn(flightInfo2);

    Reservation reservation = new Reservation(null, 1, 2, false);

    given(reservationService.getBooking(0)).willReturn(reservation);

    given(reservationService.cancelBooking(reservation.getBookId())).willReturn(true);

    reservation.setCancelled(true);

    given(reservationRepository.findBookingByID((long) 0)).willReturn(reservation);

    MockHttpServletResponse response = mvc.perform(get("/api/CancellationStatus/BookingID/0"))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    assertThat(Boolean.parseBoolean(response.getContentAsString())).isEqualTo(true);
  }
}
