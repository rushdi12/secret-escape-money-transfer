package com.secret.escape.account.transfer.manager;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.secret.escape.account.transfer.dto.AccountCreateRequestDto;
import com.secret.escape.account.transfer.dto.AccountDto;
import com.secret.escape.account.transfer.dto.UpdateEmailRequestDto;
import com.secret.escape.account.transfer.exception.BadRequestException;
import com.secret.escape.account.transfer.model.Account;
import com.secret.escape.account.transfer.repository.AccountsRepository;
import com.secret.escape.account.transfer.util.MappingUtil;

@Service
public class AccountsManagerImpl implements AccountsManager {
 
	@Autowired
	AccountsRepository accountsRepository; 
	
	@Autowired
	MappingUtil mappingUtil;
	
	@Transactional
	@Override
	public List<AccountDto> getAllAccounts(){
		
		List<Account> accounts = accountsRepository.findAll();
		
		return mappingUtil.buildAccountDetails(accounts, false);
	}
	
	@Transactional
	@Override
	public AccountDto getAccountById(Long id, Boolean loadTransactions) {

		AccountDto accountDto = null;
		Optional<Account> account = accountsRepository.findById(id);

		if (account.isPresent()) {
			accountDto = mappingUtil.mapAccountDetails(account.get(), loadTransactions);
		}

		return accountDto;
	}
	
	@Override
	public AccountDto getAccountByNumber(String accountNumber, Boolean loadTransactions)throws Exception {
		AccountDto accountDto = null;
		Account account = accountsRepository.findByAccountNumber(accountNumber);
			
		if(account != null) {
			accountDto = mappingUtil.mapAccountDetails(account, loadTransactions);
		} else {
			throw new BadRequestException("Invalid account number");
			
		}
		

		return accountDto;
	}

	@Override
	public void createAccount(AccountCreateRequestDto accountCreateRequestDto) throws Exception{
		
		Account account = new Account();
		
		account.setAccountHolderName(accountCreateRequestDto.getAccountHolderName());
		account.setAccountNumber(accountCreateRequestDto.getAccountNumber());
		account.setBalance(accountCreateRequestDto.getBalance());
		account.setEmail(accountCreateRequestDto.getEmail());
		
		accountsRepository.save(account);
		
	}

	@Override
	public void deleteAccount(String accountNumber) throws Exception {
	 
		Account account = accountsRepository.findByAccountNumber(accountNumber);
		
		if(account != null) {
			accountsRepository.delete(account);
		}
	}

	@Override
	public AccountDto updateAccount(AccountCreateRequestDto accountCreateRequestDto) throws Exception {
		AccountDto accountDto = null;
		
		Account account = accountsRepository.findByAccountNumber(accountCreateRequestDto.getAccountNumber());
		
		if(account != null) {
			 
			account.setAccountHolderName(accountCreateRequestDto.getAccountHolderName());
			account.setAccountNumber(accountCreateRequestDto.getAccountNumber());
			account.setBalance(accountCreateRequestDto.getBalance());
			account.setEmail(accountCreateRequestDto.getEmail());
			
			Account updatedAccount = accountsRepository.save(account);
			if(updatedAccount != null) {
				accountDto = mappingUtil.mapAccountDetails(updatedAccount, false);
			} else {
				throw new BadRequestException("Invalid account details");
				
			}
		
		} else {
			throw new BadRequestException("Invalid account number");
			
		}
		
		
		return accountDto;
	}

	@Override
	public AccountDto updateEmail(UpdateEmailRequestDto updateEmailRequestDto) throws Exception {
		AccountDto accountDto = null;

		Account account = accountsRepository.findByAccountNumber(updateEmailRequestDto.getAccountNumber());

		if (account != null) {
			account.setEmail(updateEmailRequestDto.getEmail());

			Account updatedAccount = accountsRepository.save(account);
			if (updatedAccount != null) {
				accountDto = mappingUtil.mapAccountDetails(updatedAccount, false);
			} else {
				throw new BadRequestException("Invalid account details");

			}

		} else {
			throw new BadRequestException("Invalid account number");

		}

		return accountDto;
	}
 
	
}
