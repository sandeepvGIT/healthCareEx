package com.svi.san.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.svi.san.constants.UserRoles;
import com.svi.san.entity.Patient;
import com.svi.san.entity.User;
import com.svi.san.exception.PatientNotFoundException;
import com.svi.san.repo.PatientRepository;
import com.svi.san.service.IPatientService;
import com.svi.san.service.IUserService;
import com.svi.san.util.MyMailUtil;
import com.svi.san.util.UserUtil;

@Service("patService")
public class PatientServiceImpl implements IPatientService {
	@Autowired
	private PatientRepository patRepo;
	@Autowired
	private IUserService usrService;
	@Autowired
	private UserUtil usrUtil;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private MyMailUtil mailUtil;

	@Override
	public Long savePatient(Patient pat) {
		Long id=patRepo.save(pat).getId();
		if(id!=null) {
			User user=new User();
			String pwd=usrUtil.generatePswrd();
			user.setDisplayName(pat.getFirstName()+" "+pat.getLastName());
			user.setUserName(pat.getMail());
			user.setPassword(pwd);
			user.setRole(UserRoles.PATIENT.name());
			Long genId=usrService.saveUser(user);
			// TODO : sending  mail to user
			if(genId!=null) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						String text="Your name is "+user.getDisplayName() +", password is "+pwd;
						mailUtil.send(user.getUserName(), "Successfully added", text);
					}
				}).start();
			}
		}
		return id;
	}

	@Override
	public List<Patient> getAllPatients() {
		return patRepo.findAll();
	}

	@Override
	public void remove(Long id) {
		patRepo.delete(getOnePatient(id));

	}

	@Override
	public Patient getOnePatient(Long id) {
		Optional<Patient> opt = patRepo.findById(id);
		if (opt.isPresent())
			return opt.get();
		else
			throw new PatientNotFoundException(id + " record not exist");
	}

	@Override
	public void updatePatient(Patient pat) {
		if(patRepo.existsById(pat.getId()))
			savePatient(pat);
		else
			throw new PatientNotFoundException(pat.getId() + " record not exist");

	}

	@Override
	public Patient getOnePatient(String userName) {
		Optional<Patient> opt=patRepo.findByMail(userName);
		if(opt.isPresent())
			return opt.get();
		else
			throw new PatientNotFoundException(userName + " record not exist");
	}

	@Override
	public Long getPatientCount() {
		return patRepo.count();
	}

}
