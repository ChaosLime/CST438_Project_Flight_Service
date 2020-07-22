package CST438.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.sun.istack.NotNull;

// TODO: what more info is required for API calls?
// TODO: input validation
@Entity
public class FlightInfo {

  @Id
  @GeneratedValue
  private long id;

  @NotNull
  private String originCity;

  @NotNull
  private String destinationCity;

  @NotNull
  private String startDate;

  @NotNull
  private String endDate;

  public FlightInfo() {
    originCity = null;
    destinationCity = null;
    startDate = null;
    endDate = null;
  }

  public FlightInfo(long id, String originCity, String destinationCity, String startDate,
      String endDate) {
    super();
    this.id = id;
    this.originCity = originCity;
    this.destinationCity = destinationCity;
    this.startDate = startDate;
    this.endDate = endDate;

  }

  public String getOriginCity() {
    return originCity;
  }

  public void setOriginCity(String originCity) {
    this.originCity = originCity;
  }

  public String getDestinationCity() {
    return destinationCity;
  }

  public void setDestinationCity(String destinationCity) {
    this.destinationCity = destinationCity;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

}
