package com.svi.san.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svi.san.entity.Specialization;
import com.svi.san.exception.SpecializationNotFoundException;
import com.svi.san.repo.SpecializationRepository;
import com.svi.san.service.ISpecializationService;
import com.svi.san.util.MyCollectionUtil;

@Service("specService")
public class SpecializationServiceImpl implements ISpecializationService {
	
	@Autowired
	private SpecializationRepository specRepo;

	@Override
	public Long saveSpecialization(Specialization spec) {
		return specRepo.save(spec).getId();
	}

	@Override
	public List<Specialization> getAllSpecialization() {
		return specRepo.findAll();
	}

	@Override
	public void removeSpecialization(Long id) {
		//specRepo.deleteById(id);
		specRepo.delete(getOneSpecailization(id));

	}

	@Override
	public Specialization getOneSpecailization(Long id) {
		/*
		Optional<Specialization> opt=specRepo.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		else {
			throw new SpecializationNotFoundException(id+"  data not found");
		}
		*/
		return specRepo.findById(id).orElseThrow(()-> new SpecializationNotFoundException(id+"  data not found"));
	}

	@Override
	public Long updateSpecialization(Specialization spec) {
		return specRepo.save(spec).getId();
	}

	@Override
	public boolean isSpecCodeExist(String specCode) {
		return specRepo.getSpecCodeCount(specCode)>0;
	}

	@Override
	public boolean isSpecNameExist(String specName) {
		return specRepo.getSpecNameCount(specName)>0;
	}

	@Override
	public boolean isSpecNoteExist(String specNote) {
		return specRepo.getSpecNoteCount(specNote)>0;
	}

	@Override
	public Map<Long, String> getSpecIdAndName() {
		List<Object[]> list=specRepo.getSpecIdAndName();
		Map<Long,String> map=MyCollectionUtil.convertToMap(list);
		System.out.println(map);
		return map;
	}

	@Override
	public Long getSpecializationCount() {
		return specRepo.count();
	}

}
