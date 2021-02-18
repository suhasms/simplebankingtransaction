package com.salesken.interview.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesken.interview.model.entity.NetbankingDetails;
import com.salesken.interview.model.repository.NetbankingDetailsRepo;
import com.salesken.interview.service.TokenService;
import com.salesken.interview.service.UserAuthenticationService;

@Service
public class TokenAuthenticationService implements UserAuthenticationService {

	@Autowired
	private NetbankingDetailsRepo netbankingDetailsRepository;

	@Autowired
	private TokenService tokenService;

	@Override
	public Optional<String> login(String username, String password) {
		final Optional<NetbankingDetails> userPresent = netbankingDetailsRepository
				.findByNbUserNameAndPassword(username, password);
		if (!userPresent.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(tokenService.createTokenForUser(userPresent.get()));
	}

	@Override
	public Optional<NetbankingDetails> findByToken(String token) {
		return tokenService.getUserForToken(token);
	}

	@Override
	public Optional<NetbankingDetails> getUserForTokenAndBank(String token, String bank) {
		if (!tokenService.verifyTokenForOtherBank(token, bank)) {
			return Optional.empty();
		}
		/* for crediting money from other bank account use administrator account */
		return netbankingDetailsRepository.findByNbUserName("administrator");

	}

	@Override
	public void logout(NetbankingDetails user) {

	}

}
