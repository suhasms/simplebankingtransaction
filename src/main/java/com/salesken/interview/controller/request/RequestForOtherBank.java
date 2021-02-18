package com.salesken.interview.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestForOtherBank {
	private String fromAccountNo;

	private String toAccountNo;

	private String fromBank;

	private float amount;

	private String token;

	public RequestForOtherBank() {
	}

}
