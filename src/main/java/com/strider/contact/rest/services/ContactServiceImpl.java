package com.strider.contact.rest.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strider.contact.rest.exception.ContactNotFoundException;
import com.strider.contact.rest.exception.NoDataFoundException;
import com.strider.contact.rest.model.Contact;
import com.strider.contact.rest.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepository;

	@Override
	public List<Contact> getContacts() {
		List<Contact> contacts = contactRepository.findAll();
		if(contacts.isEmpty()) {
			throw new NoDataFoundException();
		}
		return contacts;
	}

	@Override
	public Contact getContact(Long contactId) {
		return contactRepository.findById(contactId).orElseThrow(() -> new ContactNotFoundException(contactId));
	}

	@Override
	public Contact addContact(Contact contact) {
		contact.setActive(true);
		return contactRepository.save(contact);
	}

	@Override
	public Contact updateContact(Long contactId, Contact contact) {
		Optional<Contact> oldContact = contactRepository.findById(contactId);
		
		if (oldContact.isPresent()) {
			contact.setId(oldContact.get().getId());
			contact.setActive(oldContact.get().isActive());
			contactRepository.save(contact);
			return contact;
		}else {
			throw new ContactNotFoundException(contactId);
		}
	}

	@Override
	public void deactivateContact(Long contactId) {
		Optional<Contact> contact = contactRepository.findById(contactId);
		if (contact.isPresent()) {
			contact.get().setActive(false);
			contactRepository.save(contact.get());			
		}else {
			throw new ContactNotFoundException(contactId);
		}
	}

}
