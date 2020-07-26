/*
 * This class is used when gathering and displaying flight information for a user
 */

package CST438.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

// TODO: do we need a company attribute (ie: Southwest, United, etc)
// TODO: find out if flightNumber is an int or a String
@Entity
@Table(name="dummy_flight_data")
public class Flight {

  @NotNull
  @GeneratedValue 
  private String flightNumber;
	
  @NotNull
  @JoinColumn(name = "flightNumber")
  private FlightSeatInfo flightSeatInfo;

  @NotNull
  private String airline;

  @NotNull
  private String departureAirport;

  @NotNull
  private Date departureTime;

  @NotNull
  private String arrivalAirport;

  @NotNull
  private Date arrivalTime;
  
  @NotNull
  private Date date;

  public Flight() {

  }

  public Flight(@NotNull String flightNumber, @NotNull String airline,
      @NotNull String departureAirport, @NotNull Date departureTime, @NotNull String arrivalAirport,
      @NotNull Date arrivalTime, @NotNull FlightSeatInfo f) {
    super();
    this.flightNumber = flightNumber;
    this.airline = airline;
    this.departureAirport = departureAirport;
    this.departureTime = departureTime;
    this.arrivalAirport = arrivalAirport;
    this.arrivalTime = arrivalTime;
    this.flightSeatInfo = f;
  }

  public String getFlightNumber() {
    return flightNumber;
  }

  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }

  public Date getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(Date departureTime) {
    this.departureTime = departureTime;
  }

  public Date getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(Date arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public String getDepartureAirport() {
    return departureAirport;
  }

  public void setDepartureAirport(String departureAirport) {
    this.departureAirport = departureAirport;
  }

  public String getArrivalAirport() {
    return arrivalAirport;
  }

  public void setArrivalAirport(String arrivalAirport) {
    this.arrivalAirport = arrivalAirport;
  }

  public FlightSeatInfo getFlightSeatInfo() {
	    return flightSeatInfo;
	  }

  public void setFlightSeatInfo(FlightSeatInfo flightSeatInfo) {
	    this.flightSeatInfo = flightSeatInfo;
	  }
  
}
