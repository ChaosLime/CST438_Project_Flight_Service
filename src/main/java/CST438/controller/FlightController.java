package CST438.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import CST438.domain.FlightInfo;
import CST438.domain.FormInfo;
import CST438.service.FlightSeatInfoService;
import CST438.service.FlightService;

@Controller
public class FlightController {

  // @Autowired
  // ReservationRepository reservationRepository;

  @Autowired
  FlightService flightService;

  @Autowired
  FlightSeatInfoService seatInfoService;

  @GetMapping("/")
  public String landingPage() {
    return "landing_page";
  }

  @GetMapping("/reservation")
  public String getFormInfo(Model model) {
    FormInfo formInfo = new FormInfo();
    model.addAttribute("formInfo", formInfo);
    return "reservation_form";
  }

  @PostMapping("/flights")
  public String getAllFlights(@Valid FormInfo formInfo, BindingResult result,
      @RequestParam("originCity") String originCity,
      @RequestParam("destinationCity") String destinationCity,
      @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
      Model model) {

    if (result.hasErrors()) {
      return "reservation_form";
    }

    String flightNotFound;

    startDate = formatFlightDate(startDate);
    List<FlightInfo> departureFlights = flightService.getFlightAndSeatInfo(startDate, originCity,
        destinationCity);

    // If no departure flights are found
    if (departureFlights.isEmpty()) {
      flightNotFound = "No departure flights found, please try a different date or destination";
    } else {
      flightNotFound = "";
    }

    model.addAttribute("departNotFound", flightNotFound);
    model.addAttribute("departDate", startDate);
    model.addAttribute("departureFlights", departureFlights);

    endDate = formatFlightDate(endDate);
    List<FlightInfo> returnFlights = flightService.getFlightAndSeatInfo(endDate, destinationCity,
        originCity);

    // If no return flights are found
    if (returnFlights.isEmpty()) {
      flightNotFound = "No return flights found, please try a different date or destination";
    } else {
      flightNotFound = "";
    }

    model.addAttribute("returnNotFound", flightNotFound);
    model.addAttribute("returnDate", endDate);
    model.addAttribute("returnFlights", returnFlights);

    return "display_flight_listing";
  }

  @PostMapping("/reservationConfirmation")
  public String returnConfirmation(@RequestParam("departureFlight") int departureFlightSeatInfoId,
      @RequestParam("returnFlight") int returnFlightSeatInfoId, Model model) {

    // TODO: remove 1 seat from db and enter into booked db.
    FlightInfo departureFlight = seatInfoService.getFlight(departureFlightSeatInfoId);
    model.addAttribute("departureFlight", departureFlight);

    FlightInfo returnFlight = seatInfoService.getFlight(returnFlightSeatInfoId);
    model.addAttribute("returnFlight", returnFlight);

    double totalCost = departureFlight.getSeatInfo().getCost()
        + returnFlight.getSeatInfo().getCost();
    totalCost = Math.round(totalCost * 100d) / 100d; // two decimals only
    model.addAttribute("totalCost", totalCost);

    return "reservation_confirmation";
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
