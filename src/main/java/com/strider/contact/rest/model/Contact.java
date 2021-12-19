package com.strider.contact.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "contact")
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "First Name is mandatory")
	@Column(name = "first_name")
	private String firstName;

	@NotBlank(message = "Last Name is mandatory")
	@Column(name = "last_name")
	private String lastName;

	@Email
	@NotBlank(message = "Email is mandatory")
	@Column(name = "email")
	private String email;

	@NotBlank(message = "Phone Number is mandatory")
	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "active")
	private boolean active;
}
