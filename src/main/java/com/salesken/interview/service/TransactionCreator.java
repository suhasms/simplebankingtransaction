package com.salesken.interview.service;

import org.apache.commons.lang3.tuple.Pair;

public interface TransactionCreator {

	Pair<Boolean, String> createTransactionWithinOurBank(int userId, String fromAccountNo, String toAccountNo,
			float amount);

	Pair<Boolean, String> createTransactionFromOtherBank(int userId, String fromAccountNo, String toAccountNo,
			String fromBank, float amount);

	Pair<Boolean, String> sendRequestToOtherBankAndCreateTransaction(int userId, String accountNo, String toAccountNo,
			String fromBank, String toBank, float amount);
}
