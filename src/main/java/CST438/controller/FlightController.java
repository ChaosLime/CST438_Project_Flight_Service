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
  @PostMapping("/user_directory")
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
  @PostMapping("/new_user")
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
      System.out.println("Email [" + email + "] is not recorded in the user table");
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


    model.addAttribute("bookingInfo", reservationList);

    int seatId0, seatId1, seatId2, seatId3, seatId4;
    Reservation book0, book1, book2, book3, book4;
    FlightInfo flightInfo0, flightInfo1, flightInfo2, flightInfo3, flightInfo4;

    switch (reservationList.size()) {

      // TODO: abstract this logic to a singular function or class
      case 0:
        return "no_reservations";
      case 1:
        book0 = reservationList.get(0);
        seatId0 = book0.getDepartureFlightSeatInfoId();
        flightInfo0 = seatInfoService.getBookedFlights(seatId0);
        model.addAttribute("book0", book0);
        model.addAttribute("flightInfo0", flightInfo0);
        return "view_booking_list";
      case 2:
        book0 = reservationList.get(0);
        seatId0 = book0.getDepartureFlightSeatInfoId();
        flightInfo0 = seatInfoService.getBookedFlights(seatId0);
        book1 = reservationList.get(1);
        seatId1 = book1.getDepartureFlightSeatInfoId();
        flightInfo1 = seatInfoService.getBookedFlights(seatId1);
        model.addAttribute("book0", book0);
        model.addAttribute("book1", book1);
        model.addAttribute("flightInfo0", flightInfo0);
        model.addAttribute("flightInfo1", flightInfo1);
        return "view_booking_list";
      case 3:
        book0 = reservationList.get(0);
        seatId0 = book0.getDepartureFlightSeatInfoId();
        flightInfo0 = seatInfoService.getBookedFlights(seatId0);
        book1 = reservationList.get(1);
        seatId1 = book1.getDepartureFlightSeatInfoId();
        flightInfo1 = seatInfoService.getBookedFlights(seatId1);
        book2 = reservationList.get(2);
        seatId2 = book2.getDepartureFlightSeatInfoId();
        flightInfo2 = seatInfoService.getBookedFlights(seatId2);
        model.addAttribute("book0", book0);
        model.addAttribute("book1", book1);
        model.addAttribute("book2", book2);
        model.addAttribute("flightInfo0", flightInfo0);
        model.addAttribute("flightInfo1", flightInfo1);
        model.addAttribute("flightInfo2", flightInfo2);
        return "view_booking_list";
      case 4:
        book0 = reservationList.get(0);
        seatId0 = book0.getDepartureFlightSeatInfoId();
        flightInfo0 = seatInfoService.getBookedFlights(seatId0);
        book1 = reservationList.get(1);
        seatId1 = book1.getDepartureFlightSeatInfoId();
        flightInfo1 = seatInfoService.getBookedFlights(seatId1);
        book2 = reservationList.get(2);
        seatId2 = book2.getDepartureFlightSeatInfoId();
        flightInfo2 = seatInfoService.getBookedFlights(seatId2);
        book3 = reservationList.get(3);
        seatId3 = book3.getDepartureFlightSeatInfoId();
        flightInfo3 = seatInfoService.getBookedFlights(seatId3);
        model.addAttribute("book0", book0);
        model.addAttribute("book1", book1);
        model.addAttribute("book2", book2);
        model.addAttribute("book3", book3);
        model.addAttribute("flightInfo0", flightInfo0);
        model.addAttribute("flightInfo1", flightInfo1);
        model.addAttribute("flightInfo2", flightInfo2);
        model.addAttribute("flightInfo3", flightInfo3);
        return "view_booking_list";
      case 5:
        book0 = reservationList.get(0);
        seatId0 = book0.getDepartureFlightSeatInfoId();
        flightInfo0 = seatInfoService.getBookedFlights(seatId0);
        book1 = reservationList.get(1);
        seatId1 = book1.getDepartureFlightSeatInfoId();
        flightInfo1 = seatInfoService.getBookedFlights(seatId1);
        book2 = reservationList.get(2);
        seatId2 = book2.getDepartureFlightSeatInfoId();
        flightInfo2 = seatInfoService.getBookedFlights(seatId2);
        book3 = reservationList.get(3);
        seatId3 = book3.getDepartureFlightSeatInfoId();
        flightInfo3 = seatInfoService.getBookedFlights(seatId3);
        book4 = reservationList.get(4);
        seatId4 = book4.getDepartureFlightSeatInfoId();
        flightInfo4 = seatInfoService.getBookedFlights(seatId4);
        model.addAttribute("book0", book0);
        model.addAttribute("book1", book1);
        model.addAttribute("book2", book2);
        model.addAttribute("book3", book3);
        model.addAttribute("book4", book4);
        model.addAttribute("flightInfo0", flightInfo0);
        model.addAttribute("flightInfo1", flightInfo1);
        model.addAttribute("flightInfo2", flightInfo2);
        model.addAttribute("flightInfo3", flightInfo3);
        model.addAttribute("flightInfo4", flightInfo4);

        return "view_booking_list";
    }
    return "exceeded_max_reserve";
  }

  @PostMapping("/cancelledListing")
  public String getCxBookings(@Valid User user, BindingResult result, Model model) {

    List<Reservation> cancelledList = reservationService.getCxBookingList(user.getEmail());

    model.addAttribute("bookingInfo", cancelledList);

    // TODO: abstract this logic to a singular function or class
    int seatId0, seatId1, seatId2, seatId3, seatId4;
    Reservation book0, book1, book2, book3, book4;
    FlightInfo flightInfo0, flightInfo1, flightInfo2, flightInfo3, flightInfo4;

    switch (cancelledList.size()) {

      case 0:
        return "no_cancellations";
      case 1:
        book0 = cancelledList.get(0);
        seatId0 = book0.getDepartureFlightSeatInfoId();
        flightInfo0 = seatInfoService.getBookedFlights(seatId0);
        model.addAttribute("book0", book0);
        model.addAttribute("flightInfo0", flightInfo0);
        return "view_cx_list";
      case 2:
        book0 = cancelledList.get(0);
        seatId0 = book0.getDepartureFlightSeatInfoId();
        flightInfo0 = seatInfoService.getBookedFlights(seatId0);
        book1 = cancelledList.get(1);
        seatId1 = book1.getDepartureFlightSeatInfoId();
        flightInfo1 = seatInfoService.getBookedFlights(seatId1);
        model.addAttribute("book0", book0);
        model.addAttribute("book1", book1);
        model.addAttribute("flightInfo0", flightInfo0);
        model.addAttribute("flightInfo1", flightInfo1);
        return "view_cx_list";
      case 3:
        book0 = cancelledList.get(0);
        seatId0 = book0.getDepartureFlightSeatInfoId();
        flightInfo0 = seatInfoService.getBookedFlights(seatId0);
        book1 = cancelledList.get(1);
        seatId1 = book1.getDepartureFlightSeatInfoId();
        flightInfo1 = seatInfoService.getBookedFlights(seatId1);
        book2 = cancelledList.get(2);
        seatId2 = book2.getDepartureFlightSeatInfoId();
        flightInfo2 = seatInfoService.getBookedFlights(seatId2);
        model.addAttribute("book0", book0);
        model.addAttribute("book1", book1);
        model.addAttribute("book2", book2);
        model.addAttribute("flightInfo0", flightInfo0);
        model.addAttribute("flightInfo1", flightInfo1);
        model.addAttribute("flightInfo2", flightInfo2);
        return "view_cx_list";
      case 4:
        book0 = cancelledList.get(0);
        seatId0 = book0.getDepartureFlightSeatInfoId();
        flightInfo0 = seatInfoService.getBookedFlights(seatId0);
        book1 = cancelledList.get(1);
        seatId1 = book1.getDepartureFlightSeatInfoId();
        flightInfo1 = seatInfoService.getBookedFlights(seatId1);
        book2 = cancelledList.get(2);
        seatId2 = book2.getDepartureFlightSeatInfoId();
        flightInfo2 = seatInfoService.getBookedFlights(seatId2);
        book3 = cancelledList.get(3);
        seatId3 = book3.getDepartureFlightSeatInfoId();
        flightInfo3 = seatInfoService.getBookedFlights(seatId3);
        model.addAttribute("book0", book0);
        model.addAttribute("book1", book1);
        model.addAttribute("book2", book2);
        model.addAttribute("book3", book3);
        model.addAttribute("flightInfo0", flightInfo0);
        model.addAttribute("flightInfo1", flightInfo1);
        model.addAttribute("flightInfo2", flightInfo2);
        model.addAttribute("flightInfo3", flightInfo3);
        return "view_cx_list";
      case 5:
        book0 = cancelledList.get(0);
        seatId0 = book0.getDepartureFlightSeatInfoId();
        flightInfo0 = seatInfoService.getBookedFlights(seatId0);
        book1 = cancelledList.get(1);
        seatId1 = book1.getDepartureFlightSeatInfoId();
        flightInfo1 = seatInfoService.getBookedFlights(seatId1);
        book2 = cancelledList.get(2);
        seatId2 = book2.getDepartureFlightSeatInfoId();
        flightInfo2 = seatInfoService.getBookedFlights(seatId2);
        book3 = cancelledList.get(3);
        seatId3 = book3.getDepartureFlightSeatInfoId();
        flightInfo3 = seatInfoService.getBookedFlights(seatId3);
        book4 = cancelledList.get(4);
        seatId4 = book4.getDepartureFlightSeatInfoId();
        flightInfo4 = seatInfoService.getBookedFlights(seatId4);
        model.addAttribute("book0", book0);
        model.addAttribute("book1", book1);
        model.addAttribute("book2", book2);
        model.addAttribute("book3", book3);
        model.addAttribute("book4", book4);
        model.addAttribute("flightInfo0", flightInfo0);
        model.addAttribute("flightInfo1", flightInfo1);
        model.addAttribute("flightInfo2", flightInfo2);
        model.addAttribute("flightInfo3", flightInfo3);
        model.addAttribute("flightInfo4", flightInfo4);

        return "view_cx_list";
    }
    return "exceeded_max_reserve";
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

    if (bookingInfo != null) {
      if (cancelledSuccessfully) {

        model.addAttribute("bookingInfo", bookingInfo);
        model.addAttribute("departureFlight", departureFlight);
        model.addAttribute("returnFlight", returnFlight);

        return "cancelled";
      }
    }
    return "not_found";

  }

  @PostMapping("/viewReservation")
  public String viewReservations(@Valid Reservation bookingInfo, BindingResult result,
      Model model) {

    System.out.println("Booking Id: [" + bookingInfo.getBookId() + "]");

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

  // TODO: move this out of the controller. Into the flight Class?
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
