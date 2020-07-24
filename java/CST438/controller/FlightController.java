package CST438.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import CST438.domain.LocationInfo;
import CST438.domain.ReservationRepository;

@Controller
public class FlightController {


  // repo that holds flight reservations
  @Autowired
  ReservationRepository reservationRepository;

  @GetMapping("/reservation")
  public String getLocationInfo(Model model) {
    LocationInfo location = new LocationInfo();
    model.addAttribute("flight", location);
    return "reservation_form";
  }

  @PostMapping("/flights")
  public String getAllFlights(@RequestParam("originCity") String originCity,
      @RequestParam("destinationCity") String desinationCity,
      @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
      Model model) {

    model.addAttribute("origin", originCity);
    model.addAttribute("destination", desinationCity);
    model.addAttribute("startDate", startDate);
    model.addAttribute("endDate", endDate);

    // TODO: set up and retrieve API for geolocation for reservation/api page
    // TODO: return geolocation object from API and pass into flight API.
    // TODO: set up and retrive API for flight info.
    // TODO: use flight API object for both locations, and return object to be passed
    // into the display_flight_listing page

    return "display_flight_listing";

  }
  
  @PostMapping("/flights/add")
  public String processMovieForm(@Valid LocationInfo flight, BindingResult result, Model model) {

    // Spring validates form input via POST message from http, and if no errors then
    if (result.hasErrors()) {
      return "reservation_form";
    }
    
    reservationRepository.save(flight);

    return "saved_flights";

  }

  @GetMapping("/receipt")
  public String showRecept(Model model) {

    // TODO: pass API object created from the booking service.
    return "reservation_receipt";

  }
}
