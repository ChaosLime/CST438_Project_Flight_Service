package CST438.domain;

import javax.validation.constraints.NotNull;

public class Flight {

  @NotNull
  private int flightNumber;

  @NotNull
  private String airline;

  @NotNull
  private String departureAirport;

  @NotNull
  private String departureTime;

  @NotNull
  private String arrivalAirport;

  @NotNull
  private String arrivalTime;

  @NotNull
  private String date;

  public Flight() {

  }

  public Flight(@NotNull int flightNumber, @NotNull String airline,
      @NotNull String departureAirport, @NotNull String departureTime,
      @NotNull String arrivalAirport, @NotNull String arrivalTime, @NotNull String date) {
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