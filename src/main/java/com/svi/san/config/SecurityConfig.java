package com.svi.san.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.svi.san.constants.UserRoles;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
		
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/patient/register","/patient/save").permitAll()
		.antMatchers("/patient/all").hasAuthority(UserRoles.ADMIN.name())
		.antMatchers("/doctor/**").hasAuthority(UserRoles.ADMIN.name())
		.antMatchers("/spec/**").hasAuthority(UserRoles.ADMIN.name())
		.antMatchers("/appointment/register","/appointment/save","/appointment/all").hasAuthority(UserRoles.ADMIN.name())
		.antMatchers("/appointment/view","/appointment/viewSlot").hasAuthority(UserRoles.PATIENT.name())
		.antMatchers("/slot/all","/slot/accept","/slot/reject","/slot/dashboard").hasAuthority(UserRoles.ADMIN.name())
		.antMatchers("/slot/patient","/slot/cancel").hasAuthority(UserRoles.PATIENT.name())
		.antMatchers("/user/login","/login").permitAll()
		.antMatchers("/user/showforgot","/user/genpswrd").permitAll()
		.anyRequest().authenticated()
		
		.and()
		.formLogin()
		.loginPage("/user/login") // show Login Page
		.loginProcessingUrl("/login") //Post (do login)
		.defaultSuccessUrl("/user/setup", true)
		.failureUrl("/user/login?error=true")
		
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/user/login?logout=true");
		
		
	}

}
