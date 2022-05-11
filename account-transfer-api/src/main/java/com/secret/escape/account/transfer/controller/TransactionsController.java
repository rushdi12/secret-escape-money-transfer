package com.secret.escape.account.transfer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.secret.escape.account.transfer.dto.TransactionsDto;
import com.secret.escape.account.transfer.dto.TransactionsRequestDto;
import com.secret.escape.account.transfer.manager.TransactionsManager;

@RestController
@RequestMapping(path = "/api/v1/transactions")
public class TransactionsController {

	@Autowired
	private TransactionsManager transactionsManager;
	
	
	@GetMapping(value = "/account/{id}")
	public ResponseEntity<List<TransactionsDto>> getTransactionsByFromAccountId(@PathVariable(value = "id") Long id){
		
		List<TransactionsDto> transactions = transactionsManager.getTransactionsByAccountId(id);
		
		return new ResponseEntity<List<TransactionsDto>>(transactions, HttpStatus.OK);

	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TransactionsDto> getTransactionId(@PathVariable(value = "id") Long id){
		
		TransactionsDto transaction = transactionsManager.getTransactionsById(id);
		
		return new ResponseEntity<TransactionsDto>(transaction, HttpStatus.OK);

	}
	
	@PostMapping(value = "/account")
	public ResponseEntity<List<TransactionsDto>> getTransactionsByFromAccountNumber(@RequestBody @Valid TransactionsRequestDto transactionsRequestDto){
		
		List<TransactionsDto> transactions = transactionsManager.getTransactionsByAccountNumber(transactionsRequestDto.getAccountNumber());
		
		return new ResponseEntity<List<TransactionsDto>>(transactions, HttpStatus.OK);

	}
	
}
