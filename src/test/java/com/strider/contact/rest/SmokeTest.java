package com.strider.contact.rest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.strider.contact.rest.controller.ContactController;

@SpringBootTest
public class SmokeTest {

	@Autowired
	private ContactController contactController;

	@Test
	void contextLoads() {
		assertNotNull(contactController);
	}
}
