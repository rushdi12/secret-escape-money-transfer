package com.secret.escape.account.transfer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.secret.escape.account.transfer.dto.TransferRequestDto;
import com.secret.escape.account.transfer.dto.TransferResponseDto;
import com.secret.escape.account.transfer.manager.TransferManager;

@RestController
@RequestMapping(path = "/api/v1")
public class TransferController {
	
	@Autowired
	private TransferManager transferManager;
	
	@PostMapping(path = "/transfer")
	public ResponseEntity<TransferResponseDto> performAccountTransfer(@RequestBody @Valid TransferRequestDto transferRequest) throws Exception{
		
		TransferResponseDto tranferResponse = null;
		
		tranferResponse = transferManager.performAccountTransfer(transferRequest);	
		
		return new ResponseEntity<TransferResponseDto>(tranferResponse, HttpStatus.OK);
		
	}
	
	 
}
