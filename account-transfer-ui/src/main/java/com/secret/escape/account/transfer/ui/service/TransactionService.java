package com.secret.escape.account.transfer.ui.service;

import java.util.List;

import com.secret.escape.account.transfer.ui.dto.TransactionsDto;

public interface TransactionService {

	public List<TransactionsDto> findTransactionsForAccountId(Long id);
	
	public List<TransactionsDto> findTransactionsForAccountNumber(Long accountNumber);
}
