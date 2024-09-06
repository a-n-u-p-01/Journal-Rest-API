package com.journal.Journal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
		return new ResponseEntity<>(exception.getMessage() ,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RoleTransferRequiredException .class)
	public ResponseEntity<String> handleRoleTransferRequiredException(RoleTransferRequiredException exception) {
		return new ResponseEntity<>(exception.getMessage() ,HttpStatus.CONFLICT);
	}
	
}
