package com.salesken.interview.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "personal_details")
public class PersonalDetails {

	@Id
	private int id;

	private String name;

	private String address;

	private String phoneNumber;

	private String emailId;
}
