package com.svi.san.service;

import java.util.List;
import java.util.Map;

import com.svi.san.entity.Specialization;

public interface ISpecializationService {
	
	Long saveSpecialization(Specialization spec);
	List<Specialization> getAllSpecialization();
	void removeSpecialization(Long id);
	Specialization getOneSpecailization(Long id);
	Long updateSpecialization(Specialization spec);
	boolean isSpecCodeExist(String specCode);
	boolean isSpecNameExist(String specName);
	boolean isSpecNoteExist(String specNote);
	Map<Long,String> getSpecIdAndName();
	Long getSpecializationCount();
	

}
