package CST438.domain;

public class FlightInfo {

  private Flight flight;
  private FlightSeatInfo seatInfo;
  
  public FlightInfo() {
    
  }

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
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    
    if (!FlightInfo.class.isAssignableFrom(obj.getClass())) {
      return false;
    }
    
    final FlightInfo newFlightInfo = (FlightInfo) obj;
    
    if(flight.equals(newFlightInfo.flight) &&
       seatInfo.equals(newFlightInfo.seatInfo)) {
      return true;
    } else {
      return false;
    }
  }

}
