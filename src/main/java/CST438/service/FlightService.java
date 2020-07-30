package CST438.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CST438.domain.Flight;
import CST438.domain.FlightInfo;
import CST438.domain.FlightRepository;
import CST438.domain.FlightSeatInfo;
import CST438.domain.FlightSeatInfoRepository;

@Service
public class FlightService {

  @Autowired
  private FlightRepository flightRepository;

  @Autowired
  private FlightSeatInfoRepository seatInfoRepository;

  public List<Flight> getFlightList(String flightDate, String departureCity, String arrivalCity) {
    List<Flight> flights = flightRepository.findByDateAndAirports(flightDate, departureCity,
        arrivalCity);

    return flights;
  }

  public List<FlightInfo> getFlightAndSeatInfo(String flightDate, String departureCity,
      String arrivalCity) {
    List<Flight> flights = flightRepository.findByDateAndAirports(flightDate, departureCity,
        arrivalCity);

    List<FlightInfo> flightInfo = new ArrayList<FlightInfo>();

    for (Flight flight : flights) {
      List<FlightSeatInfo> seatInfoList = seatInfoRepository
          .findByFlightNumber(flight.getFlightNumber());

      System.out.println(flight.getFlightNumber());

      for (FlightSeatInfo seatInfo : seatInfoList) {
        flightInfo.add(new FlightInfo(flight, seatInfo));
        System.out.println("seat");
      }
    }

    for (FlightInfo f : flightInfo) {
      System.out.print(f.getFlight().getFlightNumber());
      System.out.print(" - ");
      System.out.println(f.getSeatInfo().getSeatsAvailable());
    }

    return flightInfo;
  }
}
