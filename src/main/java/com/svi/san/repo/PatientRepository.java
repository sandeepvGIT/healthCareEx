package com.svi.san.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.svi.san.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	
	public Optional<Patient> findByMail(String userName);

}
