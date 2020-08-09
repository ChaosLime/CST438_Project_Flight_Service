package CST438.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dummy_flight_seat_information")
public class FlightSeatInfo extends AbstractEntity {

  private int flightNumber;
  private int seatsAvailable;
  private String seatType;
  private double cost;

  public FlightSeatInfo() {

  }

  public FlightSeatInfo(int flightNumber, int seatsAvailable, String seatType, double cost) {

    super();
    this.flightNumber = flightNumber;
    this.seatsAvailable = seatsAvailable;
    this.seatType = seatType;
    this.cost = cost;

  }

  public int getFlightNumber() {
    return flightNumber;
  }

  public void setFlightNumber(int flightNumber) {
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

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (!FlightSeatInfo.class.isAssignableFrom(obj.getClass())) {
      return false;
    }

    final FlightSeatInfo fSI = (FlightSeatInfo) obj;

    if (flightNumber == fSI.flightNumber && seatsAvailable == fSI.seatsAvailable && cost == fSI.cost
        && seatType.equals(fSI.seatType)) {
      return true;
    } else {
      return false;
    }
  }

}
