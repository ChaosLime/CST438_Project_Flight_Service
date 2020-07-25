/*
 * This class is used when gathering and displaying flight information for a user
 */

package CST438.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

// TODO: do we need a company attribute (ie: Southwest, United, etc)
// TODO: find out if flightNumber is an int or a String
@Entity
@Table(name="flight")
public class Flight {

  @NotNull
  @GeneratedValue 
  private String flightNumber;

  @NotNull
  private double cost;

  @NotNull
  private String departureAirport;

  @NotNull
  private Date departureTime;

  @NotNull
  private String arrivalAirport;

  @NotNull
  private Date arrivalTime;

  public Flight() {

  }

  public Flight(@NotNull String flightNumber, @NotNull double cost,
      @NotNull String departureAirport, @NotNull Date departureTime, @NotNull String arrivalAirport,
      @NotNull Date arrivalTime) {
    super();
    this.flightNumber = flightNumber;
    this.cost = cost;
    this.departureAirport = departureAirport;
    this.departureTime = departureTime;
    this.arrivalAirport = arrivalAirport;
    this.arrivalTime = arrivalTime;
  }

  public String getFlightNumber() {
    return flightNumber;
  }

  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
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

  @Override
  public String toString() {
	return "Flight [flightNumber=" + flightNumber + ", cost=" + cost + ", departureAirport=" + departureAirport
			+ ", departureTime=" + departureTime + ", arrivalAirport=" + arrivalAirport + ", arrivalTime=" + arrivalTime
			+ "]";
  }
  
  

}
