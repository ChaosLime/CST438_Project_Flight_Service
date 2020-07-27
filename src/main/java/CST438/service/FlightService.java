package CST438.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CST438.domain.Flight;
import CST438.domain.FlightRepository;
import CST438.domain.LocationInfo;

@Service
public class FlightService {

  @Autowired
  private FlightRepository flightRepository;

  public List<Flight> getFlightList(LocationInfo locationInfo) {
    List<Flight> flights = flightRepository.findByDateAndAirports(locationInfo.getStartDate(),
        locationInfo.getOriginCity(), locationInfo.getDestinationCity());

    return flights;
  }

}
