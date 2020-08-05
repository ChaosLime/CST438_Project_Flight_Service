package CST438.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import CST438.domain.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  // void save(String email, int departureFlightSeatInfoId, int returnFlightSeatInfoId);


}


