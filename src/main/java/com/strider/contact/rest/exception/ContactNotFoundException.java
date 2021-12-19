package com.strider.contact.rest.exception;

public class ContactNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ContactNotFoundException(Long id) {
		super(String.format("Contact with Id %d not found", id));
	}
}
