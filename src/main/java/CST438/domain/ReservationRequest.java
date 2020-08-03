package CST438.domain;

public class ReservationRequest {
	
	private Long flightId;
	private String lastName;
	private String firstName;
	private String email;
	
	public Long getFlightId() {
		return flightId;
	}
	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "ReservationRequest [flightId=" + flightId + ", lastName=" + lastName + ", firstName=" + firstName
				+ ", email=" + email + "]";
	}
	
	
	
}
