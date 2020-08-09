package CST438.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import CST438.domain.Flight;
import CST438.domain.FlightInfo;
import CST438.domain.FlightSeatInfo;
import CST438.repository.FlightRepository;
import CST438.repository.FlightSeatInfoRepository;

@Service
public class FlightSeatInfoService {

  @Autowired
  FlightSeatInfoRepository flightSeatRepo;

  @Autowired
  FlightRepository flightRepo;

  public FlightInfo getFlight(int seatInfoId) {

    FlightSeatInfo seatInfo = flightSeatRepo.findById(seatInfoId);

    // remove a seat from available in db
    seatInfo.setSeatsAvailable(seatInfo.getSeatsAvailable() - 1);
    flightSeatRepo.save(seatInfo);

    Flight flight = flightRepo.findByFlightNumber(seatInfo.getFlightNumber());

    FlightInfo flightInfo = new FlightInfo(flight, seatInfo);

    return flightInfo;
  }

  public FlightInfo getBookedFlights(int seatInfoId) {

    FlightSeatInfo seatInfo = flightSeatRepo.findById(seatInfoId);

    Flight flight = flightRepo.findByFlightNumber(seatInfo.getFlightNumber());
    FlightInfo flightInfo = new FlightInfo(flight, seatInfo);

    return flightInfo;
  }

  public FlightInfo onlyGetFlight(int seatInfoId) {

    FlightSeatInfo seatInfo = flightSeatRepo.findById(seatInfoId);

    if (seatInfo != null) {
      Flight flight = flightRepo.findByFlightNumber(seatInfo.getFlightNumber());

      FlightInfo flightInfo = new FlightInfo(flight, seatInfo);

      return flightInfo;
    } else {
      return null;
    }
  }
}
