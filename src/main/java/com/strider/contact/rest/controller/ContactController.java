package com.strider.contact.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.strider.contact.rest.model.Contact;
import com.strider.contact.rest.services.ContactService;

@RestController
public class ContactController {
	@Autowired
	private ContactService contactService;

	// Get all contacts
	@GetMapping(value = "/contacts")
	public List<Contact> getContacts() {
		return contactService.getContacts();
	}

	// Get a contact
	@GetMapping(value = "/contacts/{id}")
	public Contact getContact(@PathVariable(value = "id") Long contactId) {
		return contactService.getContact(contactId);
	}

	// Add a contact
	@PostMapping(value = "/contacts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Contact> addContact(@RequestBody @Valid Contact contact) {
		Contact insertedContact = contactService.addContact(contact);
		return new ResponseEntity<>(insertedContact, HttpStatus.CREATED);
	}

	// Update a contact
	@PutMapping("/contacts/{id}")
	public Contact updateContact(@PathVariable(value = "id") Long contactId, @RequestBody @Valid Contact contact) {
		return contactService.updateContact(contactId, contact);
	}

	// Deactivate a contact
	@DeleteMapping("/contacts/{id}")
	public ResponseEntity<Object> deactivateContact(@PathVariable(value = "id") Long contactId) {
		contactService.deactivateContact(contactId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
