package com.svi.san.exception;

public class DoctorNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID=1L;
	public DoctorNotFoundException() {
		super();
	}
	
	public DoctorNotFoundException(String msg) {
		super(msg);
	}

}
