package com.personiv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personiv.dao.ReservationDao;
import com.personiv.model.Reservation;

@Service
public class ReservationService {
	
	@Autowired
	private ReservationDao reservationDao;
	
	public List<Reservation> getReservations(){
		return reservationDao.getReservations();
	}
}
