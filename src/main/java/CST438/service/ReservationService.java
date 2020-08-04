package CST438.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import CST438.domain.Reservation;
import CST438.repository.ReservationRepository;

@Service
public class ReservationService {

  @Autowired
  private ReservationRepository reservationRepository;

  public void bookFlight(Reservation reservation) {
    reservationRepository.save(reservation);
  }



}
