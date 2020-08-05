package CST438.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FormInfo {

  @Id
  @GeneratedValue
  private long id;
  public String originCity;
  public String destinationCity;
  public String startDate;
  public String endDate;

  public FormInfo() {
    originCity = null;
    destinationCity = null;
    startDate = java.time.LocalDate.now().toString();
    endDate = java.time.LocalDate.now().plusDays(1).toString();
  }

  public FormInfo(long id, String originCity, String destinationCity, String startDate,
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
