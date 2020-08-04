package CST438.domain;

public class FlightInfo extends AbstractEntity {

  public Flight flight;
  public FlightSeatInfo seatInfo;

  public FlightInfo(Flight flight, FlightSeatInfo seatInfo) {
    super();
    this.flight = flight;
    this.seatInfo = seatInfo;
  }

  public Flight getFlight() {
    return flight;
  }

  public void setFlight(Flight flight) {
    this.flight = flight;
  }

  public FlightSeatInfo getSeatInfo() {
    return seatInfo;
  }

  public void setSeatInfo(FlightSeatInfo seatInfo) {
    this.seatInfo = seatInfo;
  }

}
