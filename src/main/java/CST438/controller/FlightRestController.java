package CST438.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import CST438.domain.Flight;
import CST438.service.FlightService;

@RestController
public class FlightRestController {
  
  @Autowired
  private FlightService flightService;
  
  @GetMapping("/api/{flightDate}/{departureCity}/{arrivalCity}")
  public ResponseEntity<List<Flight>> getFlightList(@PathVariable("flightDate") String flightDate,
      @PathVariable("departureCity") String departureCity, @PathVariable("arrivalCity") String arrivalCity) {
    
    flightDate = flightDate.replace('-', '/');
    
    List<Flight> flightInfo = flightService.getFlightList(flightDate, departureCity, arrivalCity);
    if ( flightInfo == null) {
      // Flight List was empty.  Send 404 return code.
      return new ResponseEntity<List<Flight>>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<List<Flight>>(flightInfo, HttpStatus.OK);
    }
  }
}