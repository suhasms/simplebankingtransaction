package com.salesken.interview.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.salesken.interview.model.entity.Accounts;
import com.salesken.interview.service.SameBankTransaction;

@Service
@Transactional(rollbackOn = Exception.class)
public class SameBankTransactionImpl extends AbstractTransactions implements SameBankTransaction {

	@Value("${bank.name}")
	private String bankName;

	@Override
	public Pair<Boolean, String> sendMoneyToSameBank(int userId, String accountNo, String toAccountNo, float amount) {
		/* Lock the transaction */
		transactionLock.lock();

		final Optional<Accounts> fromAccountPresent = accountsRepo.findByAccountNoAndUserId(accountNo, userId);
		/* Return false if account not present */
		if (!fromAccountPresent.isPresent()) {
			return ImmutablePair.of(false, "Your account doesn't exist");
		}

		final Optional<Accounts> toAccountPresent = accountsRepo.findByAccountNo(toAccountNo);
		/* Return false if account not present */
		if (!toAccountPresent.isPresent()) {
			return ImmutablePair.of(false, "Can't send to the account as it doesn't exist");
		}
		final Accounts fromAccount = fromAccountPresent.get();
		if (fromAccount.getTotalBalance() < amount) {
			return ImmutablePair.of(false, "Insufficient balance");
		}
		deductFromAccount(fromAccount, amount);
		final Accounts toAccount = toAccountPresent.get();
		addToAccount(toAccount, amount);

		/* Add transaction for fromAccount, mark it as debit */
		addTransaction(fromAccount.getId(), fromAccount.getId(), fromAccount.getAccountNo(), toAccount.getAccountNo(),
				bankName, bankName, amount, fromAccount.getTotalBalance(), "debit");

		/* Add transaction for fromAccount, mark it as credit */
		addTransaction(toAccount.getId(), toAccount.getId(), fromAccount.getAccountNo(), toAccount.getAccountNo(),
				bankName, bankName, amount, toAccount.getTotalBalance(), "credit");

		/* Unlock when it's done */
		transactionLock.unlock();

		return ImmutablePair.of(true, "Succesfully sent money");
	}

	@Override
	public Pair<Boolean, String> deductMoneyFromAccountOnSuccessfulTransaction(int userId, String accountNo,
			String toBankName, String toAccountNo, float amount) {

		/* Lock the transaction */
		transactionLock.lock();

		final Optional<Accounts> fromAccountPresent = accountsRepo.findByAccountNoAndUserId(accountNo, userId);
		/* Return false if account not present */
		if (!fromAccountPresent.isPresent()) {
			return ImmutablePair.of(false, "Your account doesn't exist");
		}

		final Optional<Accounts> toAccountPresent = accountsRepo.findByAccountNo(toAccountNo);
		/* Return false if account not present */
		if (!toAccountPresent.isPresent()) {
			return ImmutablePair.of(false, "Can't send to the account as it doesn't exist");
		}
		final Accounts fromAccount = fromAccountPresent.get();
		if (fromAccount.getTotalBalance() < amount) {
			return ImmutablePair.of(false, "Insufficient balance");
		}
		deductFromAccount(fromAccount, amount);

		/* Add transaction for fromAccount, mark it as debit */
		addTransaction(fromAccount.getId(), fromAccount.getId(), fromAccount.getAccountNo(), toAccountNo, bankName,
				toBankName, amount, fromAccount.getTotalBalance(), "debit");

		/* Unlock when it's done */
		transactionLock.unlock();

		return ImmutablePair.of(true, "Succesfully sent money");
	}

}
