package CST438.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import CST438.domain.Flight;
import CST438.domain.Reservation;
import CST438.domain.ReservationRequest;
import CST438.exception.FlightNotFound;
import CST438.repository.FlightRepository;
import CST438.service.ReservationService;

import java.util.Optional;

@Controller
public class ReservationController {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ReservationService reservationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    @RequestMapping("/showCompleteReservation")
    public String showCompleteReservation(@RequestParam("flightId") Long flightId, ModelMap modelMap) {
    	
        LOGGER.info("showCompleteReservation() invoked with the Flight Id: " + flightId);
        Optional<Flight> flight = flightRepository.findById(flightId);
        
        // handle error here
        if(!flight.isPresent()) {
            LOGGER.error("Flight Not found: {}",flight.toString());
            throw new FlightNotFound("flightId "+flightId);
        }
        
        LOGGER.info("Flight found: {}",flight.toString());
        modelMap.addAttribute("flight",flight.get());
        return "reservation/completeReservation";
        
    }

    @RequestMapping(value = "/completeReservation",method = RequestMethod.POST)
    public String completeReservation(ReservationRequest reservationRequest, ModelMap modelMap) {
    	
        LOGGER.info("completeReservation() invoked with the Reservation: " + reservationRequest.toString());
        Reservation reservation=reservationService.bookFlight(reservationRequest);
        modelMap.addAttribute("msg", "Reservation created successfully and the reservation comfirmation id is " + reservation.getId());
        return "reservation/reservationConfirmation";
        
    }

}