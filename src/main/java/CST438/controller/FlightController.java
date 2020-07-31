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
import CST438.domain.Flight;
import CST438.domain.FormInfo;
import CST438.domain.User;
import CST438.service.FlightService;
import CST438.service.UserService;

@Controller
public class FlightController {

  @Autowired
  FlightService flightService;

  @Autowired
  UserService userService;

  @GetMapping("/")
  public String landingPage(@Valid User user, BindingResult result, Model model) {
    User newUser = new User();
    model.addAttribute("newUser", newUser);
    return "landing_page";
  }

  // For returning or existing users only
  @PostMapping("/user_directory")
  public String getUserDirectory(String email, Model model) {
    List<User> currentUserInfo = userService.getAccountInfo(email);

    String error = "";

    if (currentUserInfo.isEmpty()) {
      error = "Email [" + email
          + "] is not found in our database. If this is a mistake, please check spelling and try again.";
      System.out.println("Email [" + email + "] is not found in user table.");
      model.addAttribute("error", error);
      return "error_page";
    } else {
      error = "";
    }

    System.out.println("Email [" + email + "] found. Existing User.");
    model.addAttribute("userInfo", currentUserInfo);

    model.addAttribute("error", error);
    return "user_directory";
  }

  // For new user path
  @PostMapping("/new_user")
  public String getNewUserDirectory(@Valid User newUser, BindingResult result, Model model) {
    String email = newUser.getEmail();
    String first_name = newUser.getFirst_name();
    String last_name = newUser.getLast_name();

    // check if email exists already in db
    List<User> doesUserExist = userService.getAccountInfo(email);

    // Error validation and user input handling.
    String error = "";

    if (!doesUserExist.isEmpty()) {
      System.out.println("Email [" + email + "] exists already. Can not create new account.");

      error = "Account with the email [" + email
          + "] already exists. Can not make an account when it already exists.";
      model.addAttribute("error", error);
      return "error_page";

    } else {
      error = "";
      if (email.isBlank()) {
        System.out.println("Email entered was blank");
      } else {
        System.out.println("Email [" + email + "] is not recorded in the user table");
      }
    }

    // Checking user inputs for edge cases.
    if (first_name.equals("")) {
      error = "Account Creation Failed. First Name can not be left blank, please try again";
      model.addAttribute("error", error);
      return "error_page";
    } else {
      error = "";
    }
    if (last_name.equals("")) {
      error = "Account Creation Failed. Last Name can not be left blank, please try again";
      model.addAttribute("error", error);
      return "error_page";
    } else {
      error = "";
    }
    if (email.equals("")) {
      error = "Account Creation Failed. Email can not be left blank, please try again";
      model.addAttribute("error", error);
      return "error_page";
    } else {
      error = "";
    }

    System.out.println("New User generated.");
    System.out.println("Saving new user into database.");
    userService.saveIntoDatabase(newUser);

    model.addAttribute("userInfo", newUser);

    return "new_user";
  }

  @PostMapping("/reservation")
  public String getFormInfo(@Valid User userInfo, BindingResult result, Model model) {
    FormInfo formInfo = new FormInfo();
    model.addAttribute("formInfo", formInfo);
    model.addAttribute("userInfo", userInfo);
    return "reservation_form";
  }

  @PostMapping("/view_reservations")
  public String viewReservations(@Valid User userInfo, BindingResult result, Model model) {
    model.addAttribute("userInfo", userInfo);
    return "view_reservations";
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
    List<Flight> departureFlights =
        flightService.getFlightList(startDate, originCity, destinationCity);

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
    List<Flight> returnFlights = flightService.getFlightList(endDate, destinationCity, originCity);

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
