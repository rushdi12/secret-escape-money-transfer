package com.secret.escape.account.transfer.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.secret.escape.account.transfer.model.Transaction;

public interface TransactionsRepository extends JpaRepository<Transaction, Long>{

	@Query("SELECT transaction from Transaction as transaction where transaction.fromAccount.id = :accountId")
	Set<Transaction> findByFromAccountId(Long accountId);
	
	@Query("SELECT transaction from Transaction as transaction where transaction.fromAccount.accountNumber = :accountNumber")
	Set<Transaction> findTransactionByFromAccountNumber(String accountNumber);
}
