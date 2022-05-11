package com.secret.escape.account.transfer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.secret.escape.account.transfer.dto.AccountCreateRequestDto;
import com.secret.escape.account.transfer.dto.AccountDto;
import com.secret.escape.account.transfer.dto.UpdateEmailRequestDto;
import com.secret.escape.account.transfer.manager.AccountsManager;

@RestController
@RequestMapping(path = "/api/v1/accounts")
public class AccountsController {
	
	@Autowired
	private AccountsManager accountsManager;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<AccountDto>> getAllAccounts(){
		
		List<AccountDto> allAccounts = accountsManager.getAllAccounts();
		
		return new ResponseEntity<List<AccountDto>>(allAccounts, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<AccountDto> getAccountById(@PathVariable(value = "id") Long accountId){
		
		AccountDto accountDto = accountsManager.getAccountById(accountId, true);
		
		return new ResponseEntity<AccountDto>(accountDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/account-number/{account-number}")
	public ResponseEntity<AccountDto> getAccountByAccountNumber(@PathVariable(value = "account-number") String accountNumber) throws Exception{
		
		AccountDto accountDto = accountsManager.getAccountByNumber(accountNumber, true);
		
		return new ResponseEntity<AccountDto>(accountDto, HttpStatus.OK);
	}
	
	@PostMapping(path = "/create")
	public ResponseEntity<?> createAnAccount(@RequestBody AccountCreateRequestDto accountCreateRequestDto) throws Exception{
		
		accountsManager.createAccount(accountCreateRequestDto);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PutMapping(path = "/update")
	public ResponseEntity<?> updateAnAccount(@RequestBody AccountCreateRequestDto accountCreateRequestDto) throws Exception{
		
		AccountDto updateAccount = accountsManager.updateAccount(accountCreateRequestDto);
		
		return new ResponseEntity<AccountDto>(updateAccount,HttpStatus.OK);
	}
	
	@PutMapping(path = "/update/email")
	public ResponseEntity<?> updateAnAccount(@RequestBody UpdateEmailRequestDto updateEmailRequestDto) throws Exception{
		
		AccountDto updateAccount = accountsManager.updateEmail(updateEmailRequestDto);
		
		return new ResponseEntity<AccountDto>(updateAccount,HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<?> deleteAnAccount(@PathVariable(value = "id") String accountNumber) throws Exception{
		
		accountsManager.deleteAccount(accountNumber);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
