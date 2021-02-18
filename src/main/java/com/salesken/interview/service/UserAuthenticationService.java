package com.salesken.interview.service;

import java.util.Optional;

import com.salesken.interview.model.entity.NetbankingDetails;

public interface UserAuthenticationService {

	Optional<String> login(String username, String password);

	Optional<NetbankingDetails> findByToken(String token);

	void logout(NetbankingDetails user);

	Optional<NetbankingDetails> getUserForTokenAndBank(String token, String bank);
}