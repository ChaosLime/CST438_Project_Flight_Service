package CST438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightSeatInfoRepository extends JpaRepository<FlightSeatInfo, String> {
	
    List<FlightSeatInfo> findByFlightNumber(String flightNumber);
    
}