package com.secret.escape.account.transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secret.escape.account.transfer.model.Account;

public interface AccountsRepository extends JpaRepository<Account, Long>{

	public Account findByAccountNumber(String accountNumber);
}
