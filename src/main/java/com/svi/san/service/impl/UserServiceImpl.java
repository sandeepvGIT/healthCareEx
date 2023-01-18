package com.svi.san.service.impl;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.svi.san.entity.User;
import com.svi.san.repo.UserRepository;
import com.svi.san.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService,UserDetailsService {
	
	@Autowired
	private UserRepository usrRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Long saveUser(User user) {
		//encoding password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return usrRepo.save(user).getId();
	}

	@Override
	public Optional<User> findByUsername(String userName) {
		return usrRepo.findByUserName(userName);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> opt=findByUsername(userName);
		if(!opt.isPresent())
			throw new UsernameNotFoundException(userName);
		else {
			User user=opt.get();
			return new org.springframework.security.core.userdetails.User(
					                                                                                              user.getUserName(), 
					                                                                                               user.getPassword(),
					                                                                                                Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
					
		}
	}

	@Transactional
	public void updateUserPassword(String pwd, Long userId) {
		String encPwd= passwordEncoder.encode(pwd);
		usrRepo.updateUserPassword(encPwd, userId);
		
	}

	

}
