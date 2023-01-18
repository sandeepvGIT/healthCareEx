package com.svi.san.exception;

public class AppointmentNotFoundException extends RuntimeException {
	
	public AppointmentNotFoundException() {
		super();
	}
	
	public AppointmentNotFoundException(String msg) {
		super(msg);
	}

}
