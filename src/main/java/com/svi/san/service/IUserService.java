package com.svi.san.service;

import java.util.Optional;

import com.svi.san.entity.User;

public interface IUserService {
	
	public Long saveUser(User user);
	public Optional<User> findByUsername(String userName);
	public void updateUserPassword(String pwd,Long userId);

}
