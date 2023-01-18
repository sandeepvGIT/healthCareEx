package com.svi.san.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.svi.san.entity.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
	
	@Query("SELECT COUNT(specCode) FROM Specialization WHERE specCode=:specCode")
	Integer getSpecCodeCount(String specCode);
	
	@Query("SELECT COUNT(specName) FROM Specialization WHERE specName=:specName")
	Integer getSpecNameCount(String specName);
	
	@Query("SELECT COUNT(specNote) FROM Specialization WHERE specNote=:specNote")
	Integer getSpecNoteCount(String specNote);
	
	@Query("SELECT id,specName FROM Specialization")
	List<Object[]> getSpecIdAndName();

}
