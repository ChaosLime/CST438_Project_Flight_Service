package CST438.service;


import CST438.domain.Reservation;
import CST438.domain.ReservationRequest;

public interface ReservationService {
	
    public Reservation bookFlight(ReservationRequest reservationRequest);
    
}
