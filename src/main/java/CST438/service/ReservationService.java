package CST438.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import CST438.domain.Reservation;
import CST438.domain.ReservationRepository;

@Service
public class ReservationService {

	@Autowired
	ReservationRepository reservationRepository;

	private NamedParameterJdbcTemplate jdbc;

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}


	public List<Reservation> findByEmail(String email)
	{
		return reservationRepository.findByEmail(email);
	}

	public void save(Reservation reserve) {
		reservationRepository.save(reserve);

	}

	public void delete(Long id) {
		reservationRepository.deleteById(id);

	}

	public boolean create(Reservation reserve) {

		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(reserve);

		return jdbc.update("insert into reservations (comf_id, email) values (:comfId, :email)", params) == 1;
	}

}
