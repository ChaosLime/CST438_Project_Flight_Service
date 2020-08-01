package CST438.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import CST438.domain.FlightSeatInfo;
import CST438.domain.Reservation;
import CST438.service.FlightSeatInfoService;
import CST438.service.ReservationService;

@Controller
public class ReservationController {

	@Autowired
	private ReservationService reservationsService;
	
	@Autowired
	private FlightSeatInfoService seatInfoService;

	@RequestMapping(value="/myreservations", method=RequestMethod.GET)
	ModelAndView myReservations(ModelAndView modelAndView, Reservation reserve, FlightSeatInfo seat, 
			BindingResult result, Principal principal) {

		modelAndView.setViewName("app.myreservations");

		List<Reservation> reservations = reservationsService.findByEmail(principal.getName());
		HashMap<Long, FlightSeatInfo> myMap = new HashMap<Long, FlightSeatInfo>();

		
		
		FlightSeatInfo booking = new FlightSeatInfo();
		for(int i = 0; i < reservations.size(); i++) {
			
			//booking = seatInfoService.getFlight(reservations.get(i).getComfId());
			myMap.put(reservations.get(i).getId(), booking);
			
		}
		

		modelAndView.getModel().put("reservations", reservations);
		modelAndView.getModel().put("myMap", myMap);

		return modelAndView;
	}
	
	@RequestMapping(value="/myreservations", method=RequestMethod.POST)
	ModelAndView myReservations(ModelAndView modelAndView, Reservation reserve){



		reservationsService.delete(reserve.getId());
		modelAndView.setViewName("redirect:/myreservations");

		return modelAndView;
	}
}

