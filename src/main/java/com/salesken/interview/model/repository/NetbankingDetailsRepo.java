package com.salesken.interview.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesken.interview.model.entity.NetbankingDetails;

@Repository
public interface NetbankingDetailsRepo extends JpaRepository<NetbankingDetails, Integer> {

	Optional<NetbankingDetails> findByNbUserNameAndPassword(final String userName, final String password);

	Optional<NetbankingDetails> findByNbUserName(final String userName);
}
