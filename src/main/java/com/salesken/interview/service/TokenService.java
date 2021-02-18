package com.salesken.interview.service;

import java.util.Optional;

import com.salesken.interview.model.entity.NetbankingDetails;

public interface TokenService {

	String createTokenForUser(NetbankingDetails details);

	Optional<NetbankingDetails> getUserForToken(String token);

	boolean verifyTokenForOtherBank(String token, String bank);

	String getTokenForBank(String bank);
}