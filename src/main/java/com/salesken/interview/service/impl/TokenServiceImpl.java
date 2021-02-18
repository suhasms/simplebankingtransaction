package com.salesken.interview.service.impl;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.salesken.interview.model.entity.NetbankingDetails;
import com.salesken.interview.service.TokenService;

@Service
final class TokenServiceImpl implements TokenService {

	private Map<String, NetbankingDetails> tokenToUser = new ConcurrentHashMap<>();

	@Override
	public String createTokenForUser(NetbankingDetails details) {

		String randomString = generateRandomString();
		tokenToUser.put(randomString, details);
		return randomString;
	}

	@Override
	public Optional<NetbankingDetails> getUserForToken(String token) {
		return Optional.ofNullable(tokenToUser.get(token));
	}

	public String generateRandomString() {

		int length = 10;
		boolean useLetters = true;
		boolean useNumbers = false;
		return RandomStringUtils.random(length, useLetters, useNumbers);
	}

	@Override
	public boolean verifyTokenForOtherBank(String token, String bank) {
		/*
		 * This is a simple token based authentication, in real world it's much
		 * complicated and is based on encryption based.
		 */
		if ("sampleBankToken".equals(token)) {
			return true;
		}
		return false;
	}

	@Override
	public String getTokenForBank(String bank) {
		// For simplicity we are keeping simple token, sampleToken
		if ("sampleBank".equals(bank)) {
			return "sampleBankToken";
		}
		return "sampleBankToken";
	}

}