package CST438.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import CST438.domain.FlightSeatInfo;

@Repository
public interface FlightSeatInfoRepository extends JpaRepository<FlightSeatInfo, String> {
  List<FlightSeatInfo> findByFlightNumber(int flightNumber);

  FlightSeatInfo findById(int id);
}
