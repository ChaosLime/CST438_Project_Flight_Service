package CST438.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="reservations")
public class Reservation extends AbstractEntity{

    private int comfId;
    
    @OneToOne
    private User passenger;
    
    @OneToOne
    private Flight flight;

    public int getComfId() {
        return comfId;
    }

    public void setComfId(int comfId) {
        this.comfId = comfId;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "comfId=" + comfId +
                ", passenger=" + passenger +
                ", flight=" + flight +
                '}';
    }
}