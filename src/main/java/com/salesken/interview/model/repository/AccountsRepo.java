package com.salesken.interview.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesken.interview.model.entity.Accounts;

@Repository
public interface AccountsRepo extends JpaRepository<Accounts, Integer> {
	
	Optional<Accounts> findByAccountNo(String accountNo);

	Optional<Accounts> findByAccountNoAndUserId(String accountNo, int userId);
}
