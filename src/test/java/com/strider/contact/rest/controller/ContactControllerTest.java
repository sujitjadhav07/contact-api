package com.strider.contact.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.strider.contact.rest.model.Contact;
import com.strider.contact.rest.repository.ContactRepository;
import com.strider.contact.rest.services.ContactService;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {
	@Mock
	private ContactService contactService;

	@InjectMocks
	private ContactController contactController;

	@Mock
	private ContactRepository contactRepository;

	private MockMvc mockMvc;

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

		mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();
	}

	@Test
	void testGetContacts() throws Exception {
		when(contactService.getContacts()).thenReturn(contacts);

		mockMvc.perform(get("/contacts")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(2)))
				.andExpect(jsonPath("$[0].firstName", Matchers.is("Tom")));

		verify(contactService, times(1)).getContacts();
	}

	@Test
	void testGetContact() throws Exception {
		when(contactService.getContact(anyLong())).thenReturn(contact);

		mockMvc.perform(get("/contacts/{id}", "1")).andExpect(status().isOk())
				.andExpect(jsonPath("firstName", Matchers.is("Tom")));

		verify(contactService, times(1)).getContact(anyLong());
	}

	@Test
	void testAddContact() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		when(contactService.addContact(any())).thenReturn(contact);

		mockMvc.perform(
				post("/contacts/").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(contact)))
				.andExpect(status().isCreated()).andExpect(jsonPath("firstName", Matchers.is("Tom")));

		verify(contactService, times(1)).addContact(any());
	}

	@Test
	void testUpdateContact() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		when(contactService.updateContact(anyLong(), any())).thenReturn(contact);

		mockMvc.perform(
				put("/contacts/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(contact)))
				.andExpect(status().isOk()).andExpect(jsonPath("firstName", Matchers.is("Tom")));

		verify(contactService, times(1)).updateContact(anyLong(), any());
	}

	@Test
	void testDeactivateContact() throws JsonProcessingException, Exception {

		mockMvc.perform(delete("/contacts/{id}", "1")).andExpect(status().isNoContent());

		verify(contactService, times(1)).deactivateContact(anyLong());
	}
}
