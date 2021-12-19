package com.strider.contact.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strider.contact.rest.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
