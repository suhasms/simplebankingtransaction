package com.salesken.interview.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "transaction_meta_data")
public class TransactionMetaData {

	@Id
	private int id;

	private int transactionId;

	private String fromBankIfscCode;

	private String fromBankBranchName;

	private String fromBankBankName;
}
