package CST438.domain;

import javax.persistence.*;

@Entity
@Table(name = "dummy_flight_data")
public class Flight {

  @Id
  private int flightNumber;
  private String airline;
  private String departureAirport;
  private String departureTime;
  private String date;
  private String arrivalAirport;
  private String arrivalTime;

  public Flight() {
    // this(0, "airline", "departureAirport", "departureTime", "arrivalAirport",
    // "arrivalTime",
    // "date");
  }

  public Flight(int flightNumber, String airline, String departureAirport, String departureTime,
      String arrivalAirport, String arrivalTime, String date) {
    super();
    this.flightNumber = flightNumber;
    this.airline = airline;
    this.departureAirport = departureAirport;
    this.departureTime = departureTime;
    this.arrivalAirport = arrivalAirport;
    this.arrivalTime = arrivalTime;
    this.date = date;
  }

  public String getAirline() {
    return airline;
  }

  public void setAirline(String airline) {
    this.airline = airline;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public int getFlightNumber() {
    return flightNumber;
  }

  public void setFlightNumber(int flightNumber) {
    this.flightNumber = flightNumber;
  }

  public String getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(String departureTime) {
    this.departureTime = departureTime;
  }

  public String getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(String arrivalTime) {
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

}