package com.salesken.interview.service.impl;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.salesken.interview.controller.request.RequestForOtherBank;
import com.salesken.interview.service.OtherBankTransaction;
import com.salesken.interview.service.SameBankTransaction;
import com.salesken.interview.service.TokenService;
import com.salesken.interview.service.TransactionCreator;
import com.salesken.interview.utils.RequestHelper;

@Service
public class TransactionCreatorImpl implements TransactionCreator {

	@Autowired
	private SameBankTransaction sameBankTransaction;

	@Autowired
	private OtherBankTransaction otherBankTransaction;

	@Autowired
	private TokenService tokenService;

	@Value("${bank.name}")
	private String bankName;

	@Autowired
	private Environment environment;

	@Override
	public Pair<Boolean, String> createTransactionWithinOurBank(int userId, String fromAccountNo, String toAccountNo,
			float amount) {
		return sameBankTransaction.sendMoneyToSameBank(userId, fromAccountNo, toAccountNo, amount);
	}

	@Override
	public Pair<Boolean, String> createTransactionFromOtherBank(int userId, String fromAccountNo, String toAccountNo,
			String fromBank, float amount) {
		return otherBankTransaction.creditMoneyFromOtherBank(userId, fromAccountNo, fromBank, toAccountNo, amount);
	}

	@Override
	public Pair<Boolean, String> sendRequestToOtherBankAndCreateTransaction(int userId, String accountNo,
			String toAccountNo, String fromBank, String toBank, float amount) {

		final String token = tokenService.getTokenForBank(toBank);
		final String body = createRequestBody(accountNo, toAccountNo, fromBank, amount, token);

		String responseBody = "";
		try {

			final String bankPort = environment.getProperty(toBank + ".port");
			/* Url will be based on the bank, for example we are considering this url */
			responseBody = RequestHelper.sendPostRequest("http://localhost:" + bankPort + "/transaction/credit", body,
					Maps.newHashMap(), com.google.common.net.MediaType.JSON_UTF_8);
		} catch (Exception e) {
			return ImmutablePair.of(false, "Could not connect to bank");
		}

		Gson gson = new Gson();
		final TransactionResult responseFromOtherBank = gson.fromJson(responseBody, TransactionResult.class);

		if (!responseFromOtherBank.success) {
			return ImmutablePair.of(false, "Internal error while sending money to other bank");
		} else {
			return sameBankTransaction.deductMoneyFromAccountOnSuccessfulTransaction(userId, accountNo, toBank,
					toAccountNo, amount);
		}
	}

	private String createRequestBody(String fromAccountNo, String toAccountNo, String fromBank, float amount,
			String token) {
		RequestForOtherBank request = new RequestForOtherBank();
		request.setFromAccountNo(fromAccountNo);
		request.setToAccountNo(toAccountNo);
		request.setFromBank(fromBank);
		request.setAmount(amount);
		request.setToken(token);

		Gson gson = new Gson();
		return gson.toJson(request);

	}

	private class TransactionResult {

		private boolean success;
		private String message;

	}

}
