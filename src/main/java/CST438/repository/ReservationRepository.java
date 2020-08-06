package CST438.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import CST438.domain.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
	//@Query("SELECT reservations FROM Reservation reservations WHERE reservations.user_email = :email")

	
	@Query(value = "SELECT * FROM reservations WHERE user_email = ?1", nativeQuery = true)
	List<Reservation> findAllBookings(String email);

  // void save(String email, int departureFlightSeatInfoId, int returnFlightSeatInfoId);

}


