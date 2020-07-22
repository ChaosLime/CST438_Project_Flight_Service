package CST438.domain;

import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<FlightInfo, Long> {
  // @Query("select m from FlightInfo") //Query needs to be done from FlightInfo object
  // List data from database here

}
