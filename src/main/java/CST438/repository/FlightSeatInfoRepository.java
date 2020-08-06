package CST438.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import CST438.domain.FlightSeatInfo;

@Repository
public interface FlightSeatInfoRepository extends JpaRepository<FlightSeatInfo, String> {

  // @Query(value = "SELECT * FROM dummy_flight_seat_information WHERE
  // flight_number = ?1", nativeQuery = true)
  List<FlightSeatInfo> findByFlightNumber(int flightNumber);

  FlightSeatInfo findById(int id);
  
 
  
  
  
}