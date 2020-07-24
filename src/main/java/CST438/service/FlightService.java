package CST438.service;

import java.text.DateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CST438.domain.*;


@Service
public class FlightService {
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	public LocationInfo getLocationInfo(String originCity, String destinationCity, String startDate, String endDate) { // method to return relative information of
        // requested city from CityInfo class

		List<LocationInfo> flights = reservationRepository.findByStartDate(startDate);

		if (flights == null || flights.isEmpty()) { // returns null for CityServiceTest Test #2
			// (CityNotFound)
			return null;
		}

	

		LocationInfo location = flights.get(0);
		

		
		LocationInfo locationInfo = new LocationInfo();

		return locationInfo;

}
	
	

}
