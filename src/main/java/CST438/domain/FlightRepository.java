package CST438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

  @Query("SELECT flight FROM Flight flight WHERE flight.date = :date AND flight.departureAirport = :departureAirport AND flight.arrivalAirport = :arrivalAirport")
  List<Flight> findByDateAndAirports(@Param("date") String date,
      @Param("departureAirport") String departureAirport,
      @Param("arrivalAirport") String arrivalAirport);

}