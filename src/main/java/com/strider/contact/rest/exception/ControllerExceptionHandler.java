package com.strider.contact.rest.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(ContactNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorMessage> handleContactNotFoundException(ContactNotFoundException ex,
			WebRequest request) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.timestamp(new Date()).description(request.getDescription(false)).build();

		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoDataFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorMessage> handleNodataFoundException(NoDataFoundException ex, WebRequest request) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.timestamp(new Date()).description(request.getDescription(false)).build();

		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorMessage> handleMethodArgNotValid(MethodArgumentNotValidException ex) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.BAD_REQUEST.value())
				.message("Validation Error").timestamp(new Date()).description(ex.getBindingResult().getFieldError().getDefaultMessage()).build();

		return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);		
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorMessage> handleGlobalException(Exception ex, WebRequest request) {		
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message(ex.getMessage()).timestamp(new Date()).description(request.getDescription(false)).build();

		return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}		
}
