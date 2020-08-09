package CST438.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import CST438.domain.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
	//@Query("SELECT reservations FROM Reservation reservations WHERE reservations.user_email = :email")
	@Query(value = "SELECT * FROM reservations WHERE book_id = ?1", nativeQuery = true)
	Reservation findBookingByID(Long id);
	  
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(value = "UPDATE `reservations` SET `is_cancelled` = 1 WHERE (`book_id` = ?1)", nativeQuery = true)
	void cancelBooking(Long id);
	
	/*
	@Query(value = "SELECT * FROM reservations WHERE user_email = ?1", nativeQuery = true)
	List<Reservation> findAllBookings(String email);
	*/
	
	@Query(value = "SELECT * FROM reservations WHERE user_email = ?1 AND is_cancelled = '0'", nativeQuery = true)
	List<Reservation> findActiveBookings(String email);
	
	@Query(value = "SELECT * FROM reservations WHERE user_email = ?1 AND is_cancelled = '1'", nativeQuery = true)
	List<Reservation> findCxBookings(String email);
	
	

}


