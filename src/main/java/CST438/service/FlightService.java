package CST438.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CST438.domain.Flight;
import CST438.domain.FlightRepository;

@Service
public class FlightService {

  @Autowired
  private FlightRepository flightRepository;

  public List<Flight> getFlightList(String flightDate, String departureCity, String arrivalCity) {
    List<Flight> flights = flightRepository.findByDateAndAirports(flightDate, departureCity,
        arrivalCity);

    return flights;
  }

  public List<Flight> getAllFlights() {
    List<Flight> flights = flightRepository.findAllFlights();

    return flights;
  }

  public List<Flight> getFlightsArrivalAirport(String arrivalAirport) {
    List<Flight> flights = flightRepository.findByArrivalAirport(arrivalAirport);

    return flights;
  }

  public List<Flight> getFlightsByDate(String date) {
    List<Flight> flights = flightRepository.findByDate(date);

    return flights;
  }
}
