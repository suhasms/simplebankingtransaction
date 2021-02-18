package com.salesken.interview.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.salesken.interview.model.entity.Accounts;
import com.salesken.interview.model.repository.AccountsRepo;
import com.salesken.interview.service.GetAccountDetails;

@Service
public final class GetAccountDetailsImpl implements GetAccountDetails {

	@Autowired
	private AccountsRepo accountsRepo;

	@Override
	public String getAccountDetails(String accountNo, int userId) {
		final Gson gson = new Gson();
		final Optional<Accounts> account = accountsRepo.findByAccountNoAndUserId(accountNo, userId);
		if (!account.isPresent()) {
			return "Account not found";
		}
		return gson.toJson(account);
	}
}
