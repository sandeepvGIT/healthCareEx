package com.svi.san.service;

import java.util.List;

import com.svi.san.entity.Patient;

public interface IPatientService {
	
	Long savePatient(Patient pat);
	List<Patient> getAllPatients();
	void remove(Long id);
	Patient getOnePatient(Long id);
	void updatePatient(Patient pat);
	Patient getOnePatient(String userName);
	Long getPatientCount();

}
