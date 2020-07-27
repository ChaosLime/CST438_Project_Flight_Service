/*
 * This class is used for creating a flight reservation 
 */

package CST438.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

// TODO: find out how Flight class inputs into a database
// TODO: find out if masterBookingId is int or String
@Entity
public class FlightReservation {

  @Id
  @GeneratedValue
  private long id;

  @NotNull
  private int masterBookingId;

  @NotNull
  private Flight flightInformation;

  FlightReservation() {

  }

  public FlightReservation(@NotNull int masterBookingId, @NotNull Flight flightInformation) {
    super();
    this.masterBookingId = masterBookingId;
    this.flightInformation = flightInformation;
  }

}
