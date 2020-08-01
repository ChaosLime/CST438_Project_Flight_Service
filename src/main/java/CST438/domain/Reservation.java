package CST438.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="reservations")
public class Reservation {
	
	public Reservation() {}
	

	public Reservation(Long comfId, String email) {
		this.comfId = comfId;
		this.email = email;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "comf_id")
	private Long comfId;
	
	
	@Column(name = "email")
	private String email;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getComfId() {
		return comfId;
	}

	public void setComfId(Long comfId) {
		this.comfId = comfId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}