package CST438.service;

import org.slf4j.Logger;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CST438.domain.Flight;
import CST438.domain.Reservation;
import CST438.domain.ReservationRequest;
import CST438.domain.User;
import CST438.exception.FlightNotFound;
import CST438.repository.FlightRepository;
import CST438.repository.ReservationRepository;
import CST438.repository.UserRepository;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

   
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository passengerRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);


    @Override
    public Reservation bookFlight(ReservationRequest reservationRequest) {
     
        LOGGER.info("Inside bookFlight()");
        Long flightId = reservationRequest.getFlightId();
        Optional<Flight> flightOptional = flightRepository.findById(flightId);
        
        if (!flightOptional.isPresent()) {
            throw new FlightNotFound("No flight found with id "+ flightId);
        }
        
        LOGGER.info("Flight found with id: {}",flightId);
        
        Flight flight=flightOptional.get();
        User passenger = new User();
        
        passenger.setFirst_name(reservationRequest.getFirstName());
        passenger.setLast_name(reservationRequest.getLastName());
        passenger.setEmail(reservationRequest.getEmail());
        
        passengerRepository.save(passenger);
        LOGGER.info("Saved the passenger:" + passenger);

        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setPassenger(passenger);
       
        final Reservation savedReservation = reservationRepository.save(reservation);
        LOGGER.info("Saving the reservation:" + reservation);

        return savedReservation;

    }
    
}
