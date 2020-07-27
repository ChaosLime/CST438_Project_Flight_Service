package CST438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<LocationInfo, Long> {

  // List data from database here
  @Query("SELECT f FROM gqlse881o0dv9zhn f ORDER BY start")
	
  List<LocationInfo>findByStartDate(String startDate);
		
	
}
