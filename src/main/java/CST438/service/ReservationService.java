package CST438.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import CST438.domain.Reservation;
import CST438.domain.User;
import CST438.repository.ReservationRepository;

@Service
public class ReservationService {

  @Autowired
  private ReservationRepository reservationRepository;

  public void bookFlight(Reservation reservation) {
    reservationRepository.save(reservation);
  }
  
  public List<Reservation> getBookingList(String email) {
	  
	  List<Reservation> bookings = reservationRepository.findAllBookings(email);
	    return bookings;
	    
  }

}
