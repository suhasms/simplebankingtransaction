package com.salesken.interview.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {

	@Id
	private int id;

	private String userName;

	private int personalDetailsId;

}
