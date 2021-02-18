package com.salesken.interview.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesken.interview.model.entity.Transactions;

@Repository
public interface TransactionRepo extends JpaRepository<Transactions, Integer> {
}
