package CST438.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation {

  @Id
  @GeneratedValue
  private long bookId;
  private String userEmail;
  private int departureFlightSeatInfoId;
  private int returnFlightSeatInfoId;
  // TODO Bool Cancelled?

  public Reservation() {

  }

  public Reservation(String userEmail, int departureFlightSeatInfoId, int returnFlightSeatInfoId) {
    super();
    this.userEmail = userEmail;
    this.departureFlightSeatInfoId = departureFlightSeatInfoId;
    this.returnFlightSeatInfoId = returnFlightSeatInfoId;
  }

  public long getBookId() {
    return bookId;
  }

  public void setBookId(long bookId) {
    this.bookId = bookId;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public int getDepartureFlightSeatInfoId() {
    return departureFlightSeatInfoId;
  }

  public void setDepartureFlightSeatInfoId(int departureFlightSeatInfoId) {
    this.departureFlightSeatInfoId = departureFlightSeatInfoId;
  }

  public int getReturnFlightSeatInfoId() {
    return returnFlightSeatInfoId;
  }

  public void setReturnFlightSeatInfoId(int returnFlightSeatInfoId) {
    this.returnFlightSeatInfoId = returnFlightSeatInfoId;
  }

}
