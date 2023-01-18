package com.svi.san.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.svi.san.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByUserName(String userName);
	
	@Modifying
	@Query("UPDATE User SET  password=:pwd WHERE id=:userId")
	public void updateUserPassword(String pwd,Long userId);

}
