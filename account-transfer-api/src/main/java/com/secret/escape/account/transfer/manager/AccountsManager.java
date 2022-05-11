package com.secret.escape.account.transfer.manager;

import java.util.List;

import com.secret.escape.account.transfer.dto.AccountCreateRequestDto;
import com.secret.escape.account.transfer.dto.AccountDto;
import com.secret.escape.account.transfer.dto.UpdateEmailRequestDto;

public interface AccountsManager {

	public List<AccountDto> getAllAccounts();
	
	public AccountDto getAccountById(Long id, Boolean loadTransactions);

	public AccountDto getAccountByNumber(String accountNumber, Boolean loadTransactions) throws Exception;

	public void createAccount(AccountCreateRequestDto accountCreateRequestDto) throws Exception;

	public AccountDto updateAccount(AccountCreateRequestDto accountCreateRequestDto) throws Exception;

	public AccountDto updateEmail(UpdateEmailRequestDto updateEmailRequestDto) throws Exception;
	
	public void deleteAccount(String accountNumber) throws Exception;
}
