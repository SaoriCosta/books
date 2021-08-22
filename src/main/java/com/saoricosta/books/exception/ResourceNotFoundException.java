package com.saoricosta.books.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 5656104236389793196L;
	
	public ResourceNotFoundException(String exception) {
		super(exception);
		
	}

}
