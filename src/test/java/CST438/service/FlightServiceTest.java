package CST438.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import CST438.domain.Flight;
import CST438.domain.FlightInfo;
import CST438.domain.FlightRepository;
import CST438.domain.FlightSeatInfo;
import CST438.domain.FlightSeatInfoRepository;

@SpringBootTest
public class FlightServiceTest {

  @Autowired
  private FlightService flightService;

  @MockBean
  private FlightRepository flightRepo;

  @MockBean
  private FlightSeatInfoRepository seatRepo;

  @Test
  public void contextLoads() {

  }

  @Test
  public void testFlightsFound() throws Exception {
    Flight testFlight = getFakeFlight();
    List<Flight> testFlights = new ArrayList<Flight>();
    testFlights.add(testFlight);

    given(flightRepo.findByDateAndAirports(testFlight.getDate(), testFlight.getDepartureAirport(),
        testFlight.getArrivalAirport())).willReturn(testFlights);

    List<Flight> testData = flightService.getFlightList(testFlight.getDate(),
        testFlight.getDepartureAirport(), testFlight.getArrivalAirport());
    List<Flight> expectedData = new ArrayList<Flight>();
    expectedData.add(testFlight);

    assertEquals(testData, expectedData);
  }

  @Test
  public void testFlightsNotFound() {

    Flight testFlight = getFakeFlight();

    given(flightRepo.findByDateAndAirports(testFlight.getDate(), testFlight.getDepartureAirport(),
        testFlight.getArrivalAirport())).willReturn(null);

    List<Flight> testData = flightService.getFlightList(testFlight.getDate(),
        testFlight.getDepartureAirport(), testFlight.getArrivalAirport());
    List<Flight> expectedData = null;

    assertEquals(testData, expectedData);
  }

  @Test
  public void testFlightsAndSeats() {
    Flight testFlight = getFakeFlight();
    List<Flight> testFlights = new ArrayList<Flight>();
    testFlights.add(testFlight);

    given(flightRepo.findByDateAndAirports(testFlight.getDate(), testFlight.getDepartureAirport(),
        testFlight.getArrivalAirport())).willReturn(testFlights);

    FlightSeatInfo seatInfo = getFakeSeatInfo(testFlight);
    List<FlightSeatInfo> seatInfoList = new ArrayList<FlightSeatInfo>();
    seatInfoList.add(seatInfo);

    given(seatRepo.findByFlightNumber(testFlight.getFlightNumber())).willReturn(seatInfoList);

    List<FlightInfo> testData = flightService.getFlightAndSeatInfo(testFlight.getDate(),
        testFlight.getDepartureAirport(), testFlight.getArrivalAirport());
    FlightInfo flightInfo = new FlightInfo(testFlight, seatInfo);
    List<FlightInfo> expectedData = new ArrayList<FlightInfo>();
    expectedData.add(flightInfo);

    assertEquals(testData, expectedData);
  }

  private Flight getFakeFlight() {
    Flight flight = new Flight(49, "Test Airline", "Depart Airport", "3:00 AM", "Arrival Airport",
        "5:30 PM", "8-29-2020");

    return flight;
  }

  private FlightSeatInfo getFakeSeatInfo(Flight testFlight) {
    FlightSeatInfo seatInfo = new FlightSeatInfo(testFlight.getFlightNumber(), 22, "economy",
        22.34);

    return seatInfo;
  }

}
