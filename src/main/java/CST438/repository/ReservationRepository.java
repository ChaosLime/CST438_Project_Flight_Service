package CST438.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import CST438.domain.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  // void save(String email, int departureFlightSeatInfoId, int returnFlightSeatInfoId);
  @Query(value = "SELECT * FROM reservations WHERE book_Id = ?1", nativeQuery = true)
  Reservation findBookingByID(Long id);
  
  @Transactional
  @Modifying(flushAutomatically = true, clearAutomatically = true)
  @Query(value = "UPDATE `reservations` SET `is_cancelled` = 1 WHERE (`book_id` = ?1)", nativeQuery = true)
  void cancelBooking(Long id);
  
}