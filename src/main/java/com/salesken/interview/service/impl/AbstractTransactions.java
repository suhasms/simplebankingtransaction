package com.salesken.interview.service.impl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;

import com.salesken.interview.model.entity.Accounts;
import com.salesken.interview.model.entity.Transactions;
import com.salesken.interview.model.repository.AccountsRepo;
import com.salesken.interview.model.repository.TransactionRepo;

public abstract class AbstractTransactions {

	@Autowired
	protected AccountsRepo accountsRepo;

	@Autowired
	protected TransactionRepo transactionsRepo;

	/*
	 * use the same lock for transferring money from one bank to other, and
	 * duducting. Because both can happen at same time.
	 * 
	 * Making this lock static because this lock is shared in 2 classes, in both the
	 * cases, debit and credit transaction will happen, so lock should be effectivly
	 * same in both cases.
	 */
	protected final static Lock transactionLock = new ReentrantLock();

	protected void deductFromAccount(Accounts account, final float amount) {
		final float totalBalance = account.getTotalBalance();
		if (amount > totalBalance) {
			throw new RuntimeException("Insufficient Balance");
		}
		final float updatedBalance = totalBalance - amount;
		account.setTotalBalance(updatedBalance);
		accountsRepo.save(account);
	}

	protected void addToAccount(Accounts account, final float amount) {
		final float totalBalance = account.getTotalBalance();
		final float updatedBalance = totalBalance + amount;
		account.setTotalBalance(updatedBalance);
		accountsRepo.save(account);
	}

	protected void addTransaction(int accountId, int fromAccountId, String fromAccountNo, String toAccountNo,
			String fromBank, String toBank, float amount, float runningBalance, String transactionType) {
		final Transactions transaction = new Transactions();
		transaction.setAccountId(fromAccountId);
		transaction.setAmount(amount);
		transaction.setFromAccountNo(fromAccountNo);
		transaction.setToAccountNo(toAccountNo);
		transaction.setFromBank(fromBank);
		transaction.setToBank(toBank);
		transaction.setRunningBalance(runningBalance);
		transaction.setTransactionType(transactionType);
		transactionsRepo.save(transaction);
	}
}
