package CST438.controller;

import java.util.ArrayList;
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
import CST438.domain.Reservation;
import CST438.domain.User;
import CST438.service.FlightSeatInfoService;
import CST438.service.FlightService;
import CST438.service.ReservationService;
import CST438.service.UserService;

@Controller
public class FlightController {

  @Autowired
  FlightService flightService;

  @Autowired
  UserService userService;

  @Autowired
  FlightSeatInfoService seatInfoService;

  @Autowired
  ReservationService reservationService;

  @GetMapping("/")
  public String landingPage(Model model) {
    User user = new User();
    model.addAttribute("user", user);
    return "landing_page";
  }

  // For returning or existing users only
  @PostMapping("/userDirectory")
  public String getUserDirectory(@Valid User user, BindingResult result, String email,
      Model model) {
    User currentUserInfo = userService.getAccountInfo(user.getEmail());

    String error = "";

    // error validation for user, checks system if it is found or not,
    // else return error to try again.
    if (currentUserInfo == null) {
      error = "Email [" + email
          + "] is not found in our database. If this is a mistake, please check spelling and try again.";
      System.out.println("Email [" + email + "] is not found in user table.");

      model.addAttribute("error", error);
      return "error_page";
    } else {
      error = "";
    }

    System.out.println("Email [" + currentUserInfo.getEmail() + "] found. Existing User.");
    model.addAttribute("user", currentUserInfo);

    model.addAttribute("error", error);
    return "user_directory";
  }

  // For new user path
  @PostMapping("/newUser")
  public String getNewUserDirectory(@Valid User newUser, BindingResult result, Model model) {
    String email = newUser.getEmail();
    String first_name = newUser.getFirst_name();
    String last_name = newUser.getLast_name();

    // check if email exists already in db
    User doesUserExist = userService.getAccountInfo(email);

    // Error validation and user input handling.
    String error = "";

    if (doesUserExist != null) {
      System.out.println("Email [" + email + "] exists already. Can not create new account.");

      error = "Account with the email [" + email
          + "] already exists. Can not make an account when it already exists.";
      model.addAttribute("error", error);
      return "error_page";

    } else {
      error = "";
    }

    if (email.equals("")) {
      System.out.println("Email entered was blank");
    } else {
      System.out.println("Email [" + email + "] is not recorded in the user table.");
    }

    // Checking user inputs for edge cases.
    if (first_name.equals("")) {
      error = "Account Creation Failed. First Name can not be left blank.";
      model.addAttribute("error", error);
      return "error_page";
    } else {
      error = "";
    }
    if (last_name.equals("")) {
      error = "Account Creation Failed. Last Name can not be left blank.";
      model.addAttribute("error", error);
      return "error_page";
    } else {
      error = "";
    }
    if (email.equals("")) {
      error = "Account Creation Failed. Email can not be left blank.";
      model.addAttribute("error", error);
      return "error_page";
    } else {
      error = "";
    }

    Boolean validNewUser = userService.saveIntoDatabase(newUser);
    if (validNewUser) {
      System.out.println("Saving new user [" + newUser.getEmail() + "] into database.");

    } else {
      System.out.println("Issue generating new user, not saved.");
      error = "Unexpected error occured during account creation.";
      model.addAttribute("error", error);
      return "error_page";
    }

    model.addAttribute("user", newUser);

    return "new_user";
  }

  @PostMapping("/reservation")
  public String getFormInfo(@Valid User user, BindingResult result, Model model) {
    FormInfo formInfo = new FormInfo();
    model.addAttribute("formInfo", formInfo);
    model.addAttribute("user", user);
    return "reservation_form";
  }

  @PostMapping("/flights")
  public String getAllFlights(@Valid FormInfo formInfo, @Valid User user, BindingResult result,
      Model model) {

    String originCity = formInfo.getOriginCity();
    String destinationCity = formInfo.getDestinationCity();
    String startDate = formInfo.getStartDate();
    String endDate = formInfo.getEndDate();

    if (result.hasErrors()) {
      return "reservation_form";
    }

    String flightNotFound;

    startDate = formatFlightDate(startDate);
    List<FlightInfo> departureFlights =
        flightService.getFlightAndSeatInfo(startDate, originCity, destinationCity);

    // If no departure flights are found
    if (departureFlights.size() == 0) {
      flightNotFound = "No Departure Flights found, please try different date or destination.";
      model.addAttribute("error", flightNotFound);
      return "error_page";
    } else {
      flightNotFound = "";
    }

    model.addAttribute("departDate", startDate);
    model.addAttribute("departureFlights", departureFlights);

    endDate = formatFlightDate(endDate);
    List<FlightInfo> returnFlights =
        flightService.getFlightAndSeatInfo(endDate, destinationCity, originCity);

    // If no return flights are found
    if (returnFlights.isEmpty()) {
      flightNotFound = "No Return Flights found, please try a different date or destination.";
      model.addAttribute("error", flightNotFound);
      return "error_page";
    } else {
      flightNotFound = "";
    }

    model.addAttribute("returnDate", endDate);
    model.addAttribute("returnFlights", returnFlights);

    return "display_flight_listing";
  }

  @PostMapping("/reservationConfirmation")
  public String returnConfirmation(@Valid User user,
      @RequestParam("departureFlight") int departureFlightSeatInfoId,
      @RequestParam("returnFlight") int returnFlightSeatInfoId, Model model) {

    FlightInfo departureFlight = seatInfoService.getFlight(departureFlightSeatInfoId);
    model.addAttribute("departureFlight", departureFlight);
    System.out.println("Departure Flight seatID: [" + departureFlight.seatInfo.getId() + "]");

    FlightInfo returnFlight = seatInfoService.getFlight(returnFlightSeatInfoId);
    model.addAttribute("returnFlight", returnFlight);
    System.out.println("Return Flight seatID: [" + returnFlight.seatInfo.getId() + "]");

    Reservation bookingInfo = new Reservation(user.getEmail(), departureFlight.seatInfo.getId(),
        returnFlight.seatInfo.getId(), false);

    reservationService.bookFlight(bookingInfo);
    model.addAttribute("bookingInfo", bookingInfo);

    double totalCost =
        departureFlight.getSeatInfo().getCost() + returnFlight.getSeatInfo().getCost();
    totalCost = Math.round(totalCost * 100d) / 100d; // two decimals only
    model.addAttribute("totalCost", totalCost);

    return "reservation_confirmation";
  }

  @PostMapping("/reservationListing")
  public String getAllBookings(@Valid User user, BindingResult result, Model model) {

    List<Reservation> reservationList = reservationService.getBookingList(user.getEmail());

    if (reservationList.isEmpty()) {
      return "no_reservations";
    }

    List<FlightInfo> departureFlights = new ArrayList<FlightInfo>();
    List<FlightInfo> arrivalFlights = new ArrayList<FlightInfo>();

    for (int i = 0; i < reservationList.size(); i++) {
      int departureSeatId = reservationList.get(i).getDepartureFlightSeatInfoId();
      FlightInfo departureFlightInfo = seatInfoService.getBookedFlights(departureSeatId);
      departureFlights.add(departureFlightInfo);

      int arrivalSeatId = reservationList.get(i).getReturnFlightSeatInfoId();
      FlightInfo arrivalFlightInfo = seatInfoService.getBookedFlights(arrivalSeatId);
      arrivalFlights.add(arrivalFlightInfo);
    }

    model.addAttribute("bookingInfo", reservationList);
    model.addAttribute("departureFlights", departureFlights);
    model.addAttribute("arrivalFlights", arrivalFlights);

    return "view_booking_list";
  }

  @PostMapping("/cancelledListing")
  public String getCxBookings(@Valid User user, BindingResult result, Model model) {

    List<Reservation> cancelledList = reservationService.getCxBookingList(user.getEmail());

    if (cancelledList.isEmpty()) {
      return "no_cancellations";
    }

    List<FlightInfo> departureFlights = new ArrayList<FlightInfo>();
    List<FlightInfo> arrivalFlights = new ArrayList<FlightInfo>();

    for (int i = 0; i < cancelledList.size(); i++) {
      int departureSeatId = cancelledList.get(i).getDepartureFlightSeatInfoId();
      FlightInfo departureFlightInfo = seatInfoService.getBookedFlights(departureSeatId);
      departureFlights.add(departureFlightInfo);

      int arrivalSeatId = cancelledList.get(i).getReturnFlightSeatInfoId();
      FlightInfo arrivalFlightInfo = seatInfoService.getBookedFlights(arrivalSeatId);
      arrivalFlights.add(arrivalFlightInfo);
    }

    model.addAttribute("bookingInfo", cancelledList);
    model.addAttribute("departureFlights", departureFlights);
    model.addAttribute("arrivalFlights", arrivalFlights);

    return "view_cx_list";
  }

  @PostMapping("/reservationCancellation")
  public String cancelUpdate(@Valid Reservation bookingInfo, BindingResult result, Model model) {

    reservationService.updateSeat(bookingInfo.getDepartureFlightSeatInfoId());

    reservationService.updateSeat(bookingInfo.getReturnFlightSeatInfoId());

    boolean cancelledSuccessfully = reservationService.cancelBooking(bookingInfo.getBookId());

    FlightInfo departureFlight =
        seatInfoService.getBookedFlights(bookingInfo.getDepartureFlightSeatInfoId());

    FlightInfo returnFlight =
        seatInfoService.getBookedFlights(bookingInfo.getReturnFlightSeatInfoId());

    User user = userService.getAccountInfo(bookingInfo.getUserEmail());

    if (bookingInfo != null) {
      if (cancelledSuccessfully) {

        model.addAttribute("user", user);
        model.addAttribute("bookingInfo", bookingInfo);
        model.addAttribute("departureFlight", departureFlight);
        model.addAttribute("returnFlight", returnFlight);

        return "cancelled";
      }
    }
    model.addAttribute("user", user);
    return "not_found";

  }

  @PostMapping("/viewReservation")
  public String viewReservations(@RequestParam("bookingId") int bookingId, Model model) {

    System.out.println("Booking Id: [" + bookingId + "]");

    Reservation bookingInfo = reservationService.getBooking(bookingId);

    User user = userService.getAccountInfo(bookingInfo.getUserEmail());

    FlightInfo departureFlight =
        seatInfoService.getBookedFlights(bookingInfo.getDepartureFlightSeatInfoId());

    FlightInfo returnFlight =
        seatInfoService.getBookedFlights(bookingInfo.getReturnFlightSeatInfoId());

    model.addAttribute("bookingInfo", bookingInfo);
    model.addAttribute("user", user);
    model.addAttribute("departureFlight", departureFlight);
    model.addAttribute("returnFlight", returnFlight);

    return "view_reservation";
  }

  /**
   * This function exists within the controller rather than the flight class due to the way the
   * controller is returning data from the form.
   */
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
