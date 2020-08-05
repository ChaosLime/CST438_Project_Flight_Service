package CST438.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import CST438.domain.FlightInfo;
import CST438.service.FlightService;
/**
 * This API controller is designed to take three pieces of data:
 * The flight date, departure and arrival city.
 * The flight date must be formatted with dashes '-'.
 * Month and date can either have leading zeros or not.
 * Year must be for digits.
 * @author Mitchell Saunders
 *
 */
@RestController
public class FlightRestController {
  
  @Autowired
  private FlightService flightService;
  
  @GetMapping("/api/{flightDate}/{departureCity}/{arrivalCity}")
  public ResponseEntity<List<FlightInfo>> getFlightList(@PathVariable("flightDate") String flightDate,
      @PathVariable("departureCity") String departureCity, @PathVariable("arrivalCity") String arrivalCity) {
    
    // Grab the positions of the dashes in the provided date.
    int monthDashIndex = flightDate.indexOf('-', 0);
    int dayDashIndex = flightDate.indexOf('-', monthDashIndex + 1);
    
    // Extract the number from the string and cast them as integers to remove the leading zeros.
    int month = Integer.parseInt(flightDate.substring(0, monthDashIndex));
    int day = Integer.parseInt(flightDate.substring(monthDashIndex + 1, dayDashIndex));
    int year = Integer.parseInt(flightDate.substring(dayDashIndex + 1));
    
    flightDate = month + "/" + day + "/" + year;
    
    List<FlightInfo> flightInfo = flightService.getFlightAndSeatInfo(flightDate, departureCity, arrivalCity);
    if (flightInfo == null) {
      // Flight List was empty.  Send 404 return code.
      return new ResponseEntity<List<FlightInfo>>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<List<FlightInfo>>(flightInfo, HttpStatus.OK);
    }
  }
}