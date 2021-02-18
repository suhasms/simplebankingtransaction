package com.salesken.interview.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Accounts {

	@Id
	private int id;

	private String accountNo;

	private float totalBalance;

	private String accountType;

	private int userId;

}
