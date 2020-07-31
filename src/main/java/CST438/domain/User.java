package CST438.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue
  private int id;
  private String email;
  private LocalDateTime create_date = java.time.LocalDateTime.now();
  private String last_name;
  private String first_name;

  public User() {

  }

  public User(int id, String email, String first_name, LocalDateTime create_date,
      String last_name) {
    super();
    this.id = id;
    this.email = email;
    this.create_date = create_date;
    this.first_name = first_name;
    this.last_name = last_name;
  }


  public long getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDateTime getCreate_date() {
    return create_date;
  }

  public void setCreate_date(LocalDateTime localDateTime) {

    this.create_date = localDateTime;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }



}
