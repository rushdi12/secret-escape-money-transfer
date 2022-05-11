package com.secret.escape.account.transfer.manager;

import java.util.List;

import com.secret.escape.account.transfer.dto.TransactionsDto;

public interface TransactionsManager {

	public List<TransactionsDto> getTransactionsByAccountId(Long accountId);
	
	public List<TransactionsDto> getTransactionsByAccountNumber(String accountNumber);
	
	public TransactionsDto getTransactionsById(Long id);
	
	
}
