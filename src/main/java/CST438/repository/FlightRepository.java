package CST438.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import CST438.domain.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

  @Query("SELECT flight FROM Flight flight")
  List<Flight> findAllFlights();

  @Query(value = "SELECT * FROM dummy_flight_data WHERE arrivalAirport = ?1", nativeQuery = true)
  List<Flight> findByArrivalAirport(String arrivalAirport);

  @Query(value = "SELECT * FROM dummy_flight_data WHERE date = ?1", nativeQuery = true)
  List<Flight> findByDate(String date);

  @Query("SELECT flight FROM Flight flight WHERE flight.date = :date AND flight.departureAirport = :departureAirport AND flight.arrivalAirport = :arrivalAirport")
  List<Flight> findByDateAndAirports(@Param("date") String date,
      @Param("departureAirport") String departureAirport,
      @Param("arrivalAirport") String arrivalAirport);

  Flight findByFlightNumber(int flightNumber);
}