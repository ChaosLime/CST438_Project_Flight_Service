package CST438.service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import CST438.domain.FlightSeatInfo;
import CST438.domain.Reservation;
import CST438.repository.FlightRepository;
import CST438.repository.FlightSeatInfoRepository;


@SpringBootTest
public class ReservationServiceTest {
		
	@MockBean
	ReservationService reservationService;

	@MockBean
	FlightSeatInfoService flightSeatInfoService;
	
	@MockBean
	private FlightSeatInfoRepository seatRepo;
	
	@MockBean
	 private FlightRepository flightRepo;
	
	private String email = "test@testing.com";
	
	@Test
	public void testBookFlight() {
		
		Reservation testBooking = new Reservation("user_email", 575, 1143, false);
		
		given(reservationService.bookFlight(testBooking)).willReturn(true);
		
		Boolean actual = reservationService.bookFlight(testBooking);
		
		Boolean expected = true;
		
		assertThat(actual).isEqualTo(expected);				
		
	}
	
	@Test
	public void testCancelBookFlight() {
		
		Reservation testBooking = new Reservation();
		
		given(reservationService.cancelFlight(testBooking)).willReturn(true);
		
		Boolean actual = reservationService.cancelFlight(testBooking);
		
		Boolean expected = true;
		
		assertThat(actual).isEqualTo(expected);
		
	}
	
	@Test
	public void testGetBookingList() throws Exception {
		
		List<Reservation> testBookingList = new ArrayList<Reservation>();
		
		Reservation testBook1 = getMockBooking1();
		Reservation testBook2 = getMockBooking2();
		Reservation testBook3 = getMockBooking3();
		
		testBookingList.add(testBook1);
		testBookingList.add(testBook2);
		testBookingList.add(testBook3);
		
		given(reservationService.getBookingList(testBook1.getUserEmail())).willReturn(testBookingList);
		
		List<Reservation> expBookingList = reservationService.getBookingList(email);
		
		assertThat(testBookingList).isEqualTo(expBookingList);		
		
	}
	
	@Test
	public void testUpdateSeat() {
		
		FlightSeatInfo seatInfo = new FlightSeatInfo(23, 95, "economy", 945.23);

	    given(reservationService.updateSeat(seatInfo.getId())).willReturn(seatInfo);
	    
	    FlightSeatInfo actual = reservationService.updateSeat(seatInfo.getId());
	    
	    System.out.println("actual: " + actual);
	    seatInfo.setSeatsAvailable(seatInfo.getSeatsAvailable() + 1);
	    FlightSeatInfo expected = new FlightSeatInfo(23, 96, "economy", 945.23);
	    System.out.println("expected: " + expected);
	    
	    assertThat(actual).isEqualTo(expected);
	}
	
	
	private Reservation getMockBooking1() {
		
		Reservation booking = new Reservation(email, 88, 888, false);
		
		return booking;
		
	}
	
	private Reservation getMockBooking2() {
		
		Reservation booking = new Reservation(email, 88, 888, false);
		
		return booking;
		
	}

	private Reservation getMockBooking3() {
	
		Reservation booking = new Reservation(email, 88, 888, false);
	
		return booking;
	
	}
	
	
	
	

}
