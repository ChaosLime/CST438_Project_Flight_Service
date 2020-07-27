package CST438.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CST438.domain.Flight;
import CST438.domain.LocationInfo;
import CST438.domain.ReservationRepository;
import CST438.service.FlightService;

@Controller
public class FlightController {

  // @Autowired
  // ReservationRepository reservationRepository;

  @Autowired
  FlightService flightService;

  @GetMapping("/")
  public String landingPage() {
    return "landing_page";
  }

  @GetMapping("/reservation")
  public String getLocationInfo(Model model) {
    LocationInfo location = new LocationInfo();
    model.addAttribute("flight", location);
    return "reservation_form";
  }

  @PostMapping("/flights")
  public String getAllFlights(@RequestParam("originCity") String originCity,
      @RequestParam("destinationCity") String destinationCity,
      @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
      Model model) {

    startDate = formatFlightDate(startDate);

    List<Flight> departureFlights = flightService.getFlightList(startDate, originCity,
        destinationCity);
    List<Flight> returnFlights = flightService.getFlightList(endDate, destinationCity, originCity);

    model.addAttribute("flights", departureFlights);
    model.addAttribute("returnFlights", returnFlights);

    return "display_flight_listing";
  }

  private String formatFlightDate(String date) {

    String properDateFormat = date.substring(5).replace('-', '/');
    String year = date.substring(0, 4);
    properDateFormat = properDateFormat + "/" + year;

    if (properDateFormat.charAt(0) == '0') {
      properDateFormat = properDateFormat.substring(1);
    }

    return properDateFormat;
  }
}
