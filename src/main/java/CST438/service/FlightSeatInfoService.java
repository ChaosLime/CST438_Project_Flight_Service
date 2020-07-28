package CST438.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CST438.domain.*;

@Service
public class FlightSeatInfoService {

	@Autowired
	FlightSeatInfoRepository flightSeatRepo;
	
	public List<FlightSeatInfo> getSeatInfo(String flightNumber, int seatsAvailable, String seatType) {
	    
		List<FlightSeatInfo> seatInfo = flightSeatRepo.findByFlightNumber(flightNumber);

	    return seatInfo;
	    
	}

}
