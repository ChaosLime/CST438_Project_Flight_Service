package CST438.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import com.sun.istack.NotNull;

@Entity
public class FormInfo {

  @Id
  @GeneratedValue
  private long id;

  @NotNull
  @Size(min = 1, max = 25)
  public String originCity;

  @NotNull
  @Size(min = 1, max = 25)
  public String destinationCity;

  // TODO: Change @size to something more approiate?
  // Consider using datatype date or datatime?
  @NotNull
  @Size(min = 10, max = 10)
  public String startDate;

  @NotNull
  @Size(min = 10, max = 10)
  public String endDate;

  public FormInfo() {
    originCity = null;
    destinationCity = null;
    startDate = null;
    endDate = null;
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
