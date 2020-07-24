package CST438.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import CST438.domain.ReservationRepository;

@SpringBootTest
public class FlightServiceTest {
	
	@Autowired
	
	
	@MockBean
	  private ReservationRepository reservationRepository;

}
