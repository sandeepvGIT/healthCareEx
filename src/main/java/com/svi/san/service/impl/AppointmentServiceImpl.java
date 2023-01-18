package com.svi.san.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svi.san.entity.Appointment;
import com.svi.san.exception.AppointmentNotFoundException;
import com.svi.san.repo.AppointmentRepository;
import com.svi.san.service.IAppointmentService;

@Service("appService")
public class AppointmentServiceImpl implements IAppointmentService {
	
	@Autowired
	private AppointmentRepository appRepo;
	

	@Override
	public Long saveAppointment(Appointment app) {
		return appRepo.save(app).getId();
	}

	@Override
	public List<Appointment> getAllAppointments() {
		return appRepo.findAll();
	}

	@Override
	public void remove(Long id) {
		appRepo.delete(getOneAppointment(id));

	}

	@Override
	public Appointment getOneAppointment(Long id) {
		Optional<Appointment> opt=appRepo.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		else
			throw new AppointmentNotFoundException(id+" record not exist");
	}

	@Override
	public void updateAppointment(Appointment app) {
		if(appRepo.existsById(app.getId())) {
			appRepo.save(app);
		}
		else
			throw new AppointmentNotFoundException(app.getId()+" record not exist");

	}

	@Override
	public List<Object[]> getAppointmentByDoctor(Long docId) {
		return appRepo.getAppointmentByDoctor(docId);
	}

	@Override
	public List<Object[]> getAppoinmentsByDoctorEmail(String userName) {
		return appRepo.getAppoinmentsByDoctorEmail(userName);
	}

	@Override
	public void updateSlotCountForAppointment(Long id, int count) {
		appRepo.updateSlotCountForAppointment(id, count);
		
	}

	@Override
	public Long getAppointmentCount() {
		return appRepo.count();
	}

}
