package com.svi.san.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UserUtil {
	
	public String generatePswrd() {
		return UUID.randomUUID().toString().
				                                                replace("-", "").
				                                                substring(0, 8);
	}

}
