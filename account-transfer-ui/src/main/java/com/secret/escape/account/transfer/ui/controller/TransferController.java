package com.secret.escape.account.transfer.ui.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.secret.escape.account.transfer.ui.dto.AccountDto;
import com.secret.escape.account.transfer.ui.dto.TransactionsDto;
import com.secret.escape.account.transfer.ui.dto.TransferForm;
import com.secret.escape.account.transfer.ui.dto.TransferRequestDto;
import com.secret.escape.account.transfer.ui.dto.TransferResponseDto;
import com.secret.escape.account.transfer.ui.dto.ValidationResponseDto;
import com.secret.escape.account.transfer.ui.service.AccountsService;
import com.secret.escape.account.transfer.ui.service.TransferService;
import com.secret.escape.account.transfer.ui.service.TransferValidationService;
import com.secret.escape.account.transfer.ui.util.Convert;

@Controller
public class TransferController {

	
	@Autowired
	private AccountsService accountsService;
	
	@Autowired
	private TransferService transferService;
	
	@Autowired
	private TransferValidationService validationService;
	
	
	@GetMapping("/transfer")
    public String showTransfer(Model model) {
		     
		model.addAttribute("accountsList", findAllAccount());
		model.addAttribute("transferForm", new TransferForm());
		
        return "transfer"; 
    }
	
	@PostMapping("/transfer")
    public String transfer(@ModelAttribute("transferForm") @Valid TransferForm transferForm, BindingResult result, Model model) {
		
		 if (result.hasErrors()) {
				model.addAttribute("accountsList", findAllAccount());
	            
				return "transfer";
	        }
		 
		 ValidationResponseDto validationResponseDto = validationService.isValidTransfer(transferForm);
		 
		 if(!validationResponseDto.getIsValid()){
		        ObjectError error = new ObjectError("globalError", validationResponseDto.getErrorMessage());
		        result.addError(error);
		        model.addAttribute("accountsList", findAllAccount());

		        return "transfer";
		 }
		    
		    
		TransferRequestDto transferRequestDto = new TransferRequestDto();
		
		transferRequestDto.setFromAccountNumber(transferForm.getFromAccountNumber());
		transferRequestDto.setToAccountNumber(transferForm.getToAccountNumber());
		transferRequestDto.setAmount(Long.valueOf(transferForm.getAmount()));
		
		TransferResponseDto transferFunds = transferService.trransferFunds(transferRequestDto);
		
		if(transferFunds != null) {
			List<TransactionsDto> transactions = transferFunds.getFromAccount().getTransactions();
			setDisplayAmount(transactions);
			
			model.addAttribute("transactions",transactions);
		
			String balance = Convert.formatMinorDenominationAmount(String.valueOf(transferFunds.getFromAccount().getBalance()), 2, false, "\u00A3");
			model.addAttribute("balance", balance);
			model.addAttribute("success","Transfer Successful");
		}
			
			model.addAttribute("accountsList", findAllAccount());
			model.addAttribute("transferForm",transferForm);
			
			
       return "transfer"; 
    }

	private void setDisplayAmount(List<TransactionsDto> transactions) {
		for(TransactionsDto transaction : transactions) {
			
			transaction.setDisplayAmount(Convert.formatMinorDenominationAmount(String.valueOf(transaction.getAmount()), 2, false, "\u00A3"));
			
		}
	}
	
	private List<AccountDto> findAllAccount() {
		return accountsService.findAllAccounts();
		
	}
}
