package com.secret.escape.account.transfer.ui.service;

import java.util.List;

import com.secret.escape.account.transfer.ui.dto.AccountDto;

public interface AccountsService {

	public List<AccountDto> findAllAccounts();

	public AccountDto findAccountByAccountNumber(Long accountNumber);

	public AccountDto updateAccountEmail(String accountNumber, String email);
}
