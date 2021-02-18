package com.salesken.interview.controller;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.salesken.interview.service.UserAuthenticationService;

import lombok.Builder;
import lombok.Getter;

@RestController
@RequestMapping("/authentication")
final class Authentication extends AbstractController {

	@Autowired
	private UserAuthenticationService authenticationService;

	@PostMapping("/login")
	String login(@RequestParam("username") @NotNull final String username,
			@RequestParam("password") @NotNull final String password) {
		final Optional<String> token = authenticationService.login(username, password);
		if (!token.isPresent()) {

			final AuthenticationResponse invalidResponse = AuthenticationResponse.builder().code(401)
					.message("Invalid login").build();
			return gson.toJson(invalidResponse);
		}
		/* Success response */
		return gson.toJson(AuthenticationResponse.builder().code(200).token(token.get()).message("Login successful"));
	}

	@Getter
	@Builder
	private static class AuthenticationResponse {

		private int code;
		private String token;
		private String message;

	}
}
