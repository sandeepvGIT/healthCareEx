package com.svi.san.service;

import java.util.List;
import java.util.Map;

import com.svi.san.entity.Doctor;

public interface IDoctorService {
	Long saveDoctor(Doctor doc);
	List<Doctor> getAllDoctor();
	void removeDoctor(Long id);
	Doctor getOneDoctor(Long id);
	void updateDoctor(Doctor doc);
	Map<Long,String> getDoctorIdAndNames();
	List<Doctor> findDoctorBySpecId(Long specId);
	Long getDoctorCount();

}
