package com.back.exception;

public class ResourceNotFoundException extends RuntimeException {

	
	public ResourceNotFoundException() {
		super("Resource You are looking not found");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
