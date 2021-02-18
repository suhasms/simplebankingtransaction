package com.salesken.interview.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transactions {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int accountId;

	private String fromAccountNo;

	private String toAccountNo;

	private String fromBank;

	private String toBank;

	private float amount;

	private String transactionType;

	private float runningBalance;
}
