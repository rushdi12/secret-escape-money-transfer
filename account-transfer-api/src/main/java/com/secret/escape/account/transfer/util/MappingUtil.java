package com.secret.escape.account.transfer.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.secret.escape.account.transfer.dto.AccountDto;
import com.secret.escape.account.transfer.dto.TransactionsDto;
import com.secret.escape.account.transfer.model.Account;
import com.secret.escape.account.transfer.model.Transaction;

@Component
public class MappingUtil {

	
	public List<AccountDto> buildAccountDetails(List<Account> accounts,  Boolean loadTransactions) {
		
		List<AccountDto> accountDtos = new ArrayList<AccountDto>();
		
		for(Account account : accounts) {
			accountDtos.add(mapAccountDetails(account, loadTransactions));
		}
		
		return accountDtos;
	}
	
	public AccountDto mapAccountDetails(Account account, Boolean loadTransactions) {

		AccountDto accountDto = new AccountDto();
		accountDto.setId(account.getId());
		accountDto.setAccountHolderName(account.getAccountHolderName());
		accountDto.setAccountNumber(account.getAccountNumber());
		accountDto.setEmail(account.getEmail());
		accountDto.setBalance(account.getBalance());
		if(loadTransactions) {
			accountDto.setTransactions(buildTransactionDetails(account.getTransactions()));
				
		}
		return accountDto;
	}
	
    public List<TransactionsDto> buildTransactionDetails(Set<Transaction> transactions) {
		
		List<TransactionsDto> transactionDtos = new ArrayList<TransactionsDto>();
		
		for(Transaction transaction : transactions) {
			TransactionsDto transactionDto = new TransactionsDto();
			
			transactionDto.setId(transaction.getId());
			transactionDto.setAmount(transaction.getAmount());
			transactionDto.setFromAccountNumber(transaction.getFromAccount().getAccountNumber());
			transactionDto.setToAccountNumber(transaction.getToAccountNumber());
			transactionDtos.add(transactionDto);
		}
		
		return transactionDtos;
	}
	
	public TransactionsDto mapTransactionDetails(Transaction transaction) {

		TransactionsDto transactionsDto = new TransactionsDto();
		transactionsDto.setId(transaction.getId());
		transactionsDto.setAmount(transaction.getAmount());
		transactionsDto.setFromAccountNumber(transaction.getFromAccount().getAccountNumber());
		transactionsDto.setToAccountNumber(transaction.getToAccountNumber()); 

		return transactionsDto;
	}
}
