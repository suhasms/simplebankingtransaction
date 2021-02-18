package com.salesken.interview.model.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "netbankingDetailsEntity")
@Table(name = "netbanking_details")
public class NetbankingDetails {

	@Id
	private int id;

	private String nbUserName;

	private String password;

	private Timestamp lastLogin;

	private int userId;
}
