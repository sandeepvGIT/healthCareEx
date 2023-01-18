package com.svi.san.exception;

public class DocumentNotFoundException extends RuntimeException{
	
	public DocumentNotFoundException()  {
		super();
	}
	
	public DocumentNotFoundException(String msg)  {
		super(msg);
	}

}
