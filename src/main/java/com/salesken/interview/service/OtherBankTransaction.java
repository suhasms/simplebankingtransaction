package com.salesken.interview.service;

import org.apache.commons.lang3.tuple.Pair;

public interface OtherBankTransaction {

	Pair<Boolean, String> creditMoneyFromOtherBank(int userId, String accountNo, String creditorBank, String toAccountNo,
			float amount);

}
