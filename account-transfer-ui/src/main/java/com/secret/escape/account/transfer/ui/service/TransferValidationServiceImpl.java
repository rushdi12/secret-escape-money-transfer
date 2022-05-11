package com.secret.escape.account.transfer.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.secret.escape.account.transfer.ui.dto.AccountDto;
import com.secret.escape.account.transfer.ui.dto.TransferForm;
import com.secret.escape.account.transfer.ui.dto.ValidationResponseDto;

@Service
public class TransferValidationServiceImpl implements TransferValidationService {

	@Autowired
	private AccountsService accountService;

	
	@Override
	public ValidationResponseDto isValidTransfer(TransferForm transferRequest) {
		ValidationResponseDto response = new ValidationResponseDto();
		StringBuilder errorMessage = new StringBuilder(); 
		response.setIsValid(true);

		if(transferRequest.getFromAccountNumber().equals(transferRequest.getToAccountNumber())){
			response.setIsValid(false);
			errorMessage.append("[Please select a different account, accounts can't be the same]"); 
		}
		
		if(transferRequest.getAmount() <= 0) {
			response.setIsValid(false);
			errorMessage.append("[Zero amount transfer not allowed]"); 
		}
		
		AccountDto fromAccountDto = accountService.findAccountByAccountNumber(Long.valueOf(transferRequest.getFromAccountNumber()));
		
		if(transferRequest.getAmount() > fromAccountDto.getBalance()) {
			response.setIsValid(false);
			errorMessage.append("[Insufficient funds available]"); 
		}

		response.setErrorMessage(errorMessage.toString());
		
		return response;
		
	}

	
 
}
