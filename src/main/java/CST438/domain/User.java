package CST438.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User extends AbstractEntity {

  private String email;

  @GeneratedValue
  private static String create_date;

  private String last_name;

  private String first_name;

  public User() {}

  public User(int id, String email, String create_date, String last_name, String first_name) {
    super();
    this.email = email;
    this.create_date = create_date;
    this.first_name = first_name;
    this.last_name = last_name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCreate_date() {
    return create_date;
  }

  public void setCreate_date(String create_date) {
    this.create_date = create_date;
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
