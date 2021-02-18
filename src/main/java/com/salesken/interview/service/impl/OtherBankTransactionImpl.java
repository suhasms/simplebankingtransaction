package com.salesken.interview.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.salesken.interview.model.entity.Accounts;
import com.salesken.interview.service.OtherBankTransaction;

@Service
@Transactional(rollbackOn = Exception.class)
public class OtherBankTransactionImpl extends AbstractTransactions implements OtherBankTransaction {

	@Value("${bank.name}")
	private String bankName;

	@Override
	public Pair<Boolean, String> creditMoneyFromOtherBank(int userId, String fromAccountNo, String creditorBank,
			String toAccountNo, float amount) {

		transactionLock.lock();

		final Optional<Accounts> toAccountPresent = accountsRepo.findByAccountNo(toAccountNo);

		/* Return false if account not present */
		if (!toAccountPresent.isPresent()) {
			return ImmutablePair.of(false, "Can't send to the account as it doesn't exist");
		}
		final Accounts toAccount = toAccountPresent.get();
		addToAccount(toAccount, amount);

		/* Add transaction for fromAccount, mark it as credit */
		addTransaction(toAccount.getId(), toAccount.getId(), fromAccountNo, toAccount.getAccountNo(), creditorBank,
				bankName, amount, toAccount.getTotalBalance(), "credit");

		transactionLock.unlock();

		return ImmutablePair.of(true, "Succesfully recieved money");

	}

}
