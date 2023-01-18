package com.svi.san.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.svi.san.constants.UserRoles;
import com.svi.san.entity.Doctor;
import com.svi.san.entity.User;
import com.svi.san.exception.DoctorNotFoundException;
import com.svi.san.repo.DoctorRepository;
import com.svi.san.service.IDoctorService;
import com.svi.san.service.IUserService;
import com.svi.san.util.MyCollectionUtil;
import com.svi.san.util.MyMailUtil;
import com.svi.san.util.UserUtil;

@Service("docService")
public class DoctorServiceImpl implements IDoctorService {
	@Autowired
	private DoctorRepository docRepo;
	@Autowired
	private IUserService userService;
	@Autowired
	private UserUtil usrUtil;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private MyMailUtil mailUtil;

	@Override
	public Long saveDoctor(Doctor doc) {
		Long id=docRepo.save(doc).getId();
		if(id!=null) {
			User user=new User();
			String pwd=usrUtil.generatePswrd();
			user.setDisplayName(doc.getFirstName()+" "+doc.getLastName());
			user.setUserName(doc.getEmail());
			user.setPassword(pwd);
			user.setRole(UserRoles.DOCTOR.name());
			Long genId=userService.saveUser(user);
			// TODO : email is pending
			if(genId!=null) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						String text="Your name is "+doc.getEmail() +", password is "+pwd;
						mailUtil.send(user.getDisplayName(), "Successfully added", text);
					}
				}).start();
			}
		}
		return id;
	}

	@Override
	public List<Doctor> getAllDoctor() {
		return docRepo.findAll();
	}

	@Override
	public void removeDoctor(Long id) {
		docRepo.delete(getOneDoctor(id));

	}

	@Override
	public Doctor getOneDoctor(Long id) {
		Optional<Doctor> opt=docRepo.findById(id);
		if(opt.isPresent()) 
			return opt.get();
		else
			throw new DoctorNotFoundException(id+" record not found");
			
			
	}

	@Override
	public void updateDoctor(Doctor doc) {
		if(docRepo.existsById(doc.getId()))
			docRepo.save(doc);
		else
			throw new DoctorNotFoundException(doc.getId()+" not exist");
		
	}

	@Override
	public Map<Long, String> getDoctorIdAndNames() {
		List<Object[]> list=docRepo.getDoctorIdAndNames();
		return MyCollectionUtil.convertToMapEntery(list);
	}

	@Override
	public List<Doctor> findDoctorBySpecId(Long specId) {
		return docRepo.findDoctorBySpecId(specId);
	}

	@Override
	public Long getDoctorCount() {
		return docRepo.count();
	}

}
