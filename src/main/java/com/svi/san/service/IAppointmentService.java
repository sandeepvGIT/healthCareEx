package com.svi.san.service;

import java.util.List;

import com.svi.san.entity.Appointment;

public interface IAppointmentService {
	
	Long saveAppointment(Appointment pat);
	List<Appointment> getAllAppointments();
	void remove(Long id);
	Appointment getOneAppointment(Long id);
	void updateAppointment(Appointment pat);
	List<Object[]> getAppointmentByDoctor(Long docId);
	List<Object[]> getAppoinmentsByDoctorEmail(String userName);
	void updateSlotCountForAppointment(Long id,int count);
	Long getAppointmentCount();
	

}
