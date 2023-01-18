package com.svi.san.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.svi.san.entity.User;
import com.svi.san.service.IUserService;
import com.svi.san.util.MyMailUtil;
import com.svi.san.util.UserUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;
	@Autowired
	private UserUtil pwrdGen;
	@Autowired
	private MyMailUtil mailUtil;
 
	@GetMapping("/login")
	public String showLogin() {
		return "UserLogin";
	}
	
	@GetMapping("/setup")
	public String showHome(HttpSession session, Principal princ) {
		//get current userName
		String email=princ.getName();
		//get current user by email
		User user=userService.findByUsername(email).get();
		//store user to session
		session.setAttribute("userObj", user);
		return "UserHome";
	}
	
	@GetMapping("/pwdUpdate")
	public String showUpdatePwd() {
		return "PsrwdUpdate";
	}
	
	@PostMapping("/pwdUpdate")
	public String showUpdatePwd(@RequestParam String password, Model model,HttpSession session) {
		//get current user
		User user=(User)session.getAttribute("userObj");
		//get user id
		Long userId=user.getId();
		userService.updateUserPassword(password, userId);
		//TODO : Email 
		model.addAttribute("message", "Password Updated !");
		return "PsrwdUpdate";
	}
	
	@GetMapping("/profile")
	public String showUserProfile() {
		return "UserProfile";
	}
	
	@GetMapping("/showforgot")
	public String forgotPswrd() {
		return "ForgotPswrd";
	}
	@PostMapping("/genpswrd")
	public String genPswrd(@RequestParam String username,Model model) {
		Optional<User> opt=userService.findByUsername(username);
		if(opt.isPresent()) {
			//generate the password
			String pswrd=pwrdGen.generatePswrd();
			User user=opt.get();
			userService.updateUserPassword(pswrd, user.getId());
			model.addAttribute("message", "password updated successfully");
			//send email to user
			if(user.getId()!=null)
				new Thread(new Runnable() {
					public void run() {
						String text = "YOUR USERNAME IS: " + user.getDisplayName() +", AND NEW PASSWORD IS "+ pswrd;
						mailUtil.send(user.getUserName(), "PASWORD UPDATED!", text);
					}
				}).start();
		}else {//user not found
			model.addAttribute("message", "user not found!");
		}
		return "ForgotPswrd";
	}
}//UserController
