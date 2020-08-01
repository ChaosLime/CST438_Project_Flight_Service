package CST438.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CST438.domain.*;

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
  
  /*
  public FlightSeatInfo findById(int id) {
	  
	  FlightSeatInfo seat = flightSeatRepo.findOne(id);
	  
	  return seat;
	  
  }
  */

}
