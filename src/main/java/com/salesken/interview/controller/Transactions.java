package com.salesken.interview.controller;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salesken.interview.controller.request.RequestForOtherBank;
import com.salesken.interview.model.entity.NetbankingDetails;
import com.salesken.interview.service.GetAccountDetails;
import com.salesken.interview.service.TransactionCreator;
import com.salesken.interview.service.UserAuthenticationService;

import lombok.Builder;
import lombok.Getter;

@RestController
@RequestMapping("/transaction/")
public class Transactions extends AbstractController {

	@Autowired
	private TransactionCreator transactionCreator;

	@Autowired
	private UserAuthenticationService authenticationService;

	@Autowired
	private GetAccountDetails accountDetails;

	@Value("${bank.name}")
	private String bankName;

	@PostMapping("/sendMoney")
	String sendMoney(@RequestParam("from_account_no") @NotNull final String fromAccountNo,
			@RequestParam("to_account_no") final String toAccountNo, @RequestParam("to_bank") final String toBank,
			@RequestParam("amount") final float amount, @RequestParam("token") final String token) {

		final Optional<NetbankingDetails> details = authenticationService.findByToken(token);

		if (!details.isPresent()) {
			return gson.toJson(TransactionResult.builder().success(false).message("User not found"));
		}

		final Pair<Boolean, String> transactionResult = bankName.equals(toBank)
				/* Process transaction within same bank */
				? transactionCreator.createTransactionWithinOurBank(details.get().getUserId(), fromAccountNo,
						toAccountNo, amount)
				/* Process transaction outside of the bank */
				: transactionCreator.sendRequestToOtherBankAndCreateTransaction(details.get().getId(), fromAccountNo,
						toAccountNo, bankName, toBank, amount);
		return gson.toJson(
				TransactionResult.builder().success(transactionResult.getLeft()).message(transactionResult.getRight()));
	}

	@PostMapping("/credit")
	String recieveMoneyFromOtherBank(@Valid @RequestBody RequestForOtherBank requestFromOtherBank) {

		final Optional<NetbankingDetails> details = authenticationService
				.getUserForTokenAndBank(requestFromOtherBank.getToken(), bankName);

		/*
		 * This should be treated as internal error as administrator should be always
		 * present in the system
		 */
		if (!details.isPresent()) {
			return gson.toJson(TransactionResult.builder().success(false).message("Internal error"));
		}

		final Pair<Boolean, String> transactionResult = transactionCreator.createTransactionFromOtherBank(
				details.get().getId(), requestFromOtherBank.getFromAccountNo(), requestFromOtherBank.getToAccountNo(),
				requestFromOtherBank.getFromBank(), requestFromOtherBank.getAmount());
		return gson.toJson(
				TransactionResult.builder().success(transactionResult.getLeft()).message(transactionResult.getRight()));
	}

	@GetMapping("/viewAccountDetails")
	String viewAccountDetails(@RequestParam("account_no") @NotNull final String accountNo, final String token) {

		final Optional<NetbankingDetails> details = authenticationService.findByToken(token);

		if (!details.isPresent()) {
			return gson.toJson(TransactionResult.builder().success(false).message("User not found"));
		}
		return accountDetails.getAccountDetails(accountNo, details.get().getId());
	}

	@Getter
	@Builder
	private static class TransactionResult {

		private boolean success;
		private String message;

	}
}
