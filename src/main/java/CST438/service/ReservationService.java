package CST438.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import CST438.domain.FlightSeatInfo;
import CST438.domain.Reservation;
import CST438.repository.FlightSeatInfoRepository;
import CST438.repository.ReservationRepository;

@Service
public class ReservationService {

  @Autowired
  private ReservationRepository reservationRepository;

  @Autowired
  private FlightSeatInfoRepository flightSeatRepo;

  public boolean bookFlight(Reservation reservation) {
    reservationRepository.save(reservation);
    return true;
  }

  public boolean cancelFlight(Reservation reservation) {
    reservationRepository.save(reservation);
    return true;
  }

  public Reservation getBooking(long id) {

    Reservation booking = reservationRepository.findBookingByID(id);
    return booking;
  }

  public List<Reservation> getBookingList(String email) {

    List<Reservation> bookings = reservationRepository.findActiveBookings(email);
    return bookings;
  }

  public List<Reservation> getCxBookingList(String email) {

    List<Reservation> cx_bookings = reservationRepository.findCxBookings(email);
    return cx_bookings;
  }

  public boolean cancelBooking(long id) {

    reservationRepository.cancelBooking(id);

    Reservation booking = reservationRepository.findBookingByID(id);

    if (booking.getIsCancelled()) {
      return true;
    } else {
      return false;
    }
  }

  public FlightSeatInfo updateSeat(int seatInfoId) {

    FlightSeatInfo seatInfo = flightSeatRepo.findById(seatInfoId);

    seatInfo.setSeatsAvailable(seatInfo.getSeatsAvailable() + 1);
    flightSeatRepo.save(seatInfo);
    return seatInfo;
  }
  
}
