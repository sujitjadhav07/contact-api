package com.strider.contact.rest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.strider.contact.rest.model.Contact;
import com.strider.contact.rest.repository.ContactRepository;

@ExtendWith(MockitoExtension.class)
class ContactServiceImplTest {
	@Mock
	ContactRepository contactRepository;

	@InjectMocks
	ContactServiceImpl contactService;

	private Contact contact;

	private List<Contact> contacts;

	@BeforeEach
	void setUp() throws Exception {
		contact = Contact.builder().id(1L).firstName("Tom").lastName("Cook").email("tomcook@abc.com")
				.phoneNumber("43245778").active(true).build();

		contacts = new ArrayList<>();
		contacts.add(contact);
		contacts.add(Contact.builder().id(2L).firstName("Alex").lastName("Smith").email("alexsmith@bb.com")
				.phoneNumber("78082920").active(true).build());

	}

	@Test
	void testGetContacts() {
		when(contactRepository.findAll()).thenReturn(contacts);

		List<Contact> contactList = contactService.getContacts();

		assertEquals(2, contactList.size());

		verify(contactRepository, times(1)).findAll();
	}

	@Test
	void testGetContact() {
		when(contactRepository.findById(anyLong())).thenReturn(Optional.of(contact));

		Contact contact = contactService.getContact(1L);

		assertEquals("Tom", contact.getFirstName());

		verify(contactRepository, times(1)).findById(anyLong());
	}

	@Test
	void testAddContact() {
		when(contactRepository.save(any())).thenReturn(contact);

		Contact contact = contactService.addContact(Contact.builder().firstName("Tom").lastName("Cook")
				.email("tomcook@abc.com").phoneNumber("43245778").active(true).build());

		assertEquals(1L, contact.getId());

		verify(contactRepository, times(1)).save(any());
	}

	@Test
	void testUpdateContact() {
		when(contactRepository.findById(anyLong())).thenReturn(Optional.of(contact));
		when(contactRepository.save(any())).thenReturn(contact);

		Contact contact = contactService.updateContact(1L,Contact.builder().firstName("Tom").lastName("Cook")
				.email("tomcook@abc.com").phoneNumber("43245778").active(true).build());

		assertEquals(1L, contact.getId());

		verify(contactRepository, times(1)).save(any());
	}

	@Test
	void testDeactivateContact() {
		when(contactRepository.findById(anyLong())).thenReturn(Optional.of(contact));
		when(contactRepository.save(any())).thenReturn(contact);
		
		contactService.deactivateContact(1L);
		
		verify(contactRepository, times(1)).save(any());
	}

}
