package com.app.core.exceptions;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionHelper extends Exception {
	
	

	public ExceptionHelper(String message){
		//return new ResponseEntity<String>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
