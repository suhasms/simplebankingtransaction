package com.salesken.interview.service;

import org.apache.commons.lang3.tuple.Pair;

public interface SameBankTransaction {

	Pair<Boolean, String> sendMoneyToSameBank(int userId, String accountNo, String toAccountNo, float amount);

	Pair<Boolean, String> deductMoneyFromAccountOnSuccessfulTransaction(int userId, String accountNo, String bank,
			String toAccountNo, float amount);

}
