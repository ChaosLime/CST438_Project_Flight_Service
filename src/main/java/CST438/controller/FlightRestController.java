package CST438.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import CST438.domain.FlightInfo;
import CST438.domain.Reservation;
import CST438.service.FlightSeatInfoService;
import CST438.service.FlightService;
import CST438.service.ReservationService;

/**
 * This API controller is designed to take three pieces of data: The flight date, departure and
 * arrival city. The flight date must be formatted with dashes '-'. Month and date can either have
 * leading zeros or not. Year must be for digits.
 * 
 * @author Mitchell Saunders
 *
 */
@RestController
public class FlightRestController {

  @Autowired
  private FlightService flightService;
  
  @Autowired
  private ReservationService reservationService;
  
  @Autowired
  private FlightSeatInfoService flightSeatInfoService;

  @GetMapping("/api/FlightDate/{flightDate}/DepartureCity/{departureCity}/ArrivalCity/{arrivalCity}")
  public ResponseEntity<List<FlightInfo>> getFlightList(
      @PathVariable("flightDate") String flightDate,
      @PathVariable("departureCity") String departureCity,
      @PathVariable("arrivalCity") String arrivalCity) {

    // Grab the positions of the dashes in the provided date.
    int monthDashIndex = flightDate.indexOf('-', 0);
    int dayDashIndex = flightDate.indexOf('-', monthDashIndex + 1);

    // Extract the number from the string and cast them as integers to remove the leading zeros.
    int month = Integer.parseInt(flightDate.substring(0, monthDashIndex));
    int day = Integer.parseInt(flightDate.substring(monthDashIndex + 1, dayDashIndex));
    int year = Integer.parseInt(flightDate.substring(dayDashIndex + 1));

    flightDate = month + "/" + day + "/" + year;

    List<FlightInfo> flightInfo =
        flightService.getFlightAndSeatInfo(flightDate, departureCity, arrivalCity);
    if (flightInfo.isEmpty()) {
      // Flight List was empty. Send 404 return code.
      return new ResponseEntity<List<FlightInfo>>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<List<FlightInfo>>(flightInfo, HttpStatus.OK);
    }
  }
  
  @GetMapping("/api/SeatID1/{seatId1}/SeatID2/{seatId2}")
  public ResponseEntity<Long> getReservationFlightInfo(
      @PathVariable("seatId1") String seatId1,
      @PathVariable("seatId2") String seatId2) { 
    
    // Checks to make sure that there is such a seat id.
    FlightInfo flightInfoCheck2 = flightSeatInfoService.onlyGetFlight(Integer.parseInt(seatId2));
    FlightInfo flightInfoCheck1 = flightSeatInfoService.onlyGetFlight(Integer.parseInt(seatId1));
    
    
    if (flightInfoCheck1 != null && flightInfoCheck2 != null) {
      
      // Now actually reserve the seats.
      flightSeatInfoService.getFlight(Integer.parseInt(seatId1));
      flightSeatInfoService.getFlight(Integer.parseInt(seatId2));
    
      Reservation booking = new Reservation(null, Integer.parseInt(seatId1),
                                                Integer.parseInt(seatId2));
    
      reservationService.bookFlight(booking);
      
      long bookingId = booking.getBookId();
      
      return new ResponseEntity<Long>(bookingId, HttpStatus.OK);
    } else {
      return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
    }
  }
  
  
  @GetMapping("/api/BookingID/{bookingID}")
  public ResponseEntity<List<FlightInfo>> getReservationFlightInfo(
      @PathVariable("bookingID") String bookingID) {

    Reservation booking = reservationService.getBooking(Long.parseLong(bookingID));
    
    if (booking != null) {
    
      int seatId1 = booking.getDepartureFlightSeatInfoId();
      int seatId2 = booking.getReturnFlightSeatInfoId();
      
      FlightInfo flight1 = flightSeatInfoService.onlyGetFlight(seatId1);
      FlightInfo flight2 = flightSeatInfoService.onlyGetFlight(seatId2);    
      
      List<FlightInfo> roundTrip = new ArrayList<FlightInfo>();
      
      roundTrip.add(flight1);
      roundTrip.add(flight2);
      
      return new ResponseEntity<List<FlightInfo>>(roundTrip, HttpStatus.OK);
    } else {
      return new ResponseEntity<List<FlightInfo>>(HttpStatus.NOT_FOUND);
    }
  }
  
  @GetMapping("/api/Cancel/BookingID/{bookingID}")
  public ResponseEntity<HttpStatus> cancelReservationFlightInfo(
      @PathVariable("bookingID") String bookingId) {

    Reservation booking = reservationService.getBooking(Long.parseLong(bookingId));
    
    if (booking != null) {
      
      boolean cancelledSuccessfully = reservationService.cancelBooking(booking.getBookId());
    
      if (cancelledSuccessfully) {
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
      } else {
        return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
      }
      
    } else {
      return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
    }
  }
}
