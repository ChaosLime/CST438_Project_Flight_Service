package CST438.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import CST438.domain.FlightInfo;

@Service
public class FlightListService {
	
	private static final Logger log = LoggerFactory.getLogger(FlightListService.class);
	private RestTemplate restTemplate;
	private String flightListUrl;
	
	public FlightListService (@Value("${flightList.url}") final String flightListUrl) {
		
		this.restTemplate = new RestTemplate();
		this.flightListUrl = flightListUrl;
		
	}
	
	public FlightInfo getFlightList() {
		
		ResponseEntity<FlightInfo> response = restTemplate.getForEntity(flightListUrl, FlightInfo.class);
		FlightInfo flightInfo = response.getBody();
		log.info("Status code:" + response.getStatusCodeValue());
		return flightInfo;
		
	}
	
}
