package com.clean.architecture.core.exceptions;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	protected String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
