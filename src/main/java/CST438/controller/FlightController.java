package CST438.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import CST438.domain.LocationInfo;
import CST438.domain.FlightRepository;
import CST438.service.FlightListService;
import CST438.domain.FlightInfo;

@Controller
public class FlightController {


  // repo that holds flight reservations
  @Autowired
  FlightRepository reservationRepository;
  
  @Autowired
  FlightListService flightListService;

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
  
  @GetMapping("/flights/list")
  public String listFlights (Model model, HttpServletRequest request, HttpServletResponse response) {
      
      FlightInfo flightList = flightListService.getFlightList();
      model.addAttribute("flightList", flightList.getFlights());
      return "display_flight_listing";
      
  }

  @GetMapping("/receipt")
  public String showRecept(Model model) {

    // TODO: pass API object created from the booking service.
    return "reservation_receipt";

  }
}
