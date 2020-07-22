package CST438.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import CST438.domain.FlightInfo;

@Controller
public class FlightController {

  // @Autowired
  // MovieRatingRepository movieRatingRepository;

  @GetMapping("/reservation/new")
  public String createRating(Model model) {
    FlightInfo flight = new FlightInfo();
    model.addAttribute("flight", flight);
    return "reservation_form";
  }

  /*
   * @PostMapping("/movies") public String processRatingForm(@Valid Rating rating, BindingResult
   * result, Model model) { if(result.hasErrors()){ return "rating_form"; }
   * movieRatingRepository.save(rating); newestID = rating.getId(); return ("redirect:/movies"); }
   */
  @GetMapping("/flights")
  public String getAllRatings(Model model) {
    // displays all ratings by title and chronological order desc
    // TODO: pass object from database to be displayed into display_flight_listing
    // Iterable<FlightInfo> flight = ReservationRepository.findAll();
    // model.addAttribute("ratings", rating);
    // inputs value for the most recent new rating and returns it when added.
    // model.addAttribute("mostRecentRating", newestID);

    return "display_flight_listing";
  }
}
