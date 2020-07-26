/*
 * This class is used when gathering and displaying flight information for a user
 */

package CST438.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

// TODO: do we need a company attribute (ie: Southwest, United, etc)
// TODO: find out if flightNumber is an int or a String
@Entity
@Table(name="dummy_flight_seat_information")
public class FlightSeatInfo {

  @Id
  @GeneratedValue
  private int id;
  
  @NotNull
  private String flightNumber;

  @NotNull
  private double cost;

  @NotNull
  private int seatsAvailable;

  @NotNull
  private String seatType;

  public FlightSeatInfo() {

  }

  public FlightSeatInfo(@NotNull String flightNumber, @NotNull int seatsAvailable, @NotNull String seatType,
		  @NotNull double cost) {
	  
    super();
    this.flightNumber = flightNumber;
    this.seatsAvailable = seatsAvailable;
    this.seatType = seatType;
    this.cost = cost;
    
  }

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
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

public int getSeatsAvailable() {
	return seatsAvailable;
}

public void setSeatsAvailable(int seatsAvailable) {
	this.seatsAvailable = seatsAvailable;
}

public String getSeatType() {
	return seatType;
}

public void setSeatType(String seatType) {
	this.seatType = seatType;
}

  
  

}
