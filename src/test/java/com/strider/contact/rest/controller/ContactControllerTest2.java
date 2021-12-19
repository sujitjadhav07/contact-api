package com.strider.contact.rest.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.strider.contact.rest.exception.ContactNotFoundException;
import com.strider.contact.rest.exception.NoDataFoundException;
import com.strider.contact.rest.model.Contact;
import com.strider.contact.rest.services.ContactService;

@WebMvcTest(ContactController.class)
class ContactControllerTest2 {
	@MockBean
	private ContactService contactService;

	@Autowired
	MockMvc mockMvc;

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
	void testGetContactNotFound() throws Exception {
		when(contactService.getContact(anyLong())).thenThrow(new ContactNotFoundException(anyLong()));
		mockMvc.perform(get("/contacts/{id}", "5")).andExpect(status().isNotFound())
				.andExpect(jsonPath("statusCode", Matchers.is(404)));

		verify(contactService, times(1)).getContact(anyLong());
	}
	
	@Test
	void testAddContactNotValid() throws Exception {
		Contact contact = Contact.builder().firstName("Eric").build();
		
		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(
				post("/contacts/").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(contact)))
				.andExpect(status().isBadRequest());

		verifyNoInteractions(contactService);
	}
	
	@Test
	void testGetContactsNoDataFound() throws Exception {
		when(contactService.getContacts()).thenThrow(new NoDataFoundException());
		mockMvc.perform(get("/contacts")).andExpect(status().isNotFound())
				.andExpect(jsonPath("statusCode", Matchers.is(404)));

		verify(contactService, times(1)).getContacts();
	}

}
