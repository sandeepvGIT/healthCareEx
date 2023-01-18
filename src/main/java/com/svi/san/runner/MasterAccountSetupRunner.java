package com.svi.san.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.svi.san.constants.UserRoles;
import com.svi.san.entity.User;
import com.svi.san.service.IUserService;
import com.svi.san.util.MyMailUtil;
import com.svi.san.util.UserUtil;

@Component
public class MasterAccountSetupRunner implements CommandLineRunner {
	
	@Value("${master.user.name}")
	private String displayName;
	@Value("${master.user.email}")
	private String userName;
	@Autowired
	private IUserService userService;
	@Autowired
	private UserUtil usrUtil;
	@Autowired
	private MyMailUtil  mailUtil;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		
		if(!userService.findByUsername(userName).isPresent()) {
			String pwd=usrUtil.generatePswrd();
			User user=new User();
			user.setDisplayName(displayName);
			user.setPassword(pwd);
			user.setUserName(userName);
			user.setRole(UserRoles.ADMIN.name());
			Long genId=userService.saveUser(user);
			if(genId!=null) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						String text="Your name is "+displayName +", password is "+pwd;
						mailUtil.send(userName, "Admin added", text);
					}
				}).start();
			}
			
		}

	}

}
