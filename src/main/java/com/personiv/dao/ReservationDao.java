package com.personiv.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.personiv.model.Reservation;

@Repository
@Transactional(readOnly = false)
public class ReservationDao extends JdbcDaoSupport{

	private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
        jdbcTemplate = getJdbcTemplate();
    }
    
    public List<Reservation> getReservations(){
    	String sql ="SELECT id,title,remarks,startDate 'start',endDate 'end',allDay,repeatId FROM reservations";
    	return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Reservation>(Reservation.class));
    	
    }
}
