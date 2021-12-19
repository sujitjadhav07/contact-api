package com.strider.contact.rest.services;

import java.util.List;

import com.strider.contact.rest.model.Contact;

public interface ContactService {
	public List<Contact> getContacts();

	public Contact getContact(Long contactId);

	public Contact addContact(Contact contact);

	public Contact updateContact(Long contactId, Contact contact);

	public void deactivateContact(Long contactId);
}
