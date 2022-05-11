package com.secret.escape.account.transfer.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.secret.escape.account.transfer.ui.dto.AccountDto;
import com.secret.escape.account.transfer.ui.dto.AccountsForm;
import com.secret.escape.account.transfer.ui.dto.TransactionsDto;
import com.secret.escape.account.transfer.ui.service.AccountsService;
import com.secret.escape.account.transfer.ui.util.Convert;

@Controller
public class TransactionsController {
	
	@Autowired
	private AccountsService accountsService;
    
	
	@GetMapping("/transactions/{id}")
    public String showTransactions(@PathVariable(value = "id") Long id) {
		     
        return "transactions"; 
    }
	
	@PostMapping("/transactions/account-number")
    public String showTransactionsForAccountNumber(@ModelAttribute("accountsForm") AccountsForm form,  Model model) {
		
		AccountDto account = accountsService.findAccountByAccountNumber(form.getAccountNumber());
		
		setDisplayAmount(account.getTransactions());
		
		model.addAttribute("transactions", accountsService.findAccountByAccountNumber(form.getAccountNumber()));
		
		 List<AccountDto> findAllAccounts = accountsService.findAllAccounts();
		 model.addAttribute("accountsForm", new AccountsForm());
		 model.addAttribute("accountsList", findAllAccounts);
		
		 
       return "transactions"; 
    }
	
	@PostMapping("/transactions")
    public String showTransactions(@ModelAttribute("accountsForm") AccountsForm form, Model model) {
		     
		
		AccountDto account = accountsService.findAccountByAccountNumber(form.getAccountNumber());
		
		if(account != null){
			setDisplayAmount(account.getTransactions());
			
			model.addAttribute("transactions", account.getTransactions());
			model.addAttribute("account", account);
			String balance = Convert.formatMinorDenominationAmount(String.valueOf(account.getBalance()), 2, false, "\u00A3");
			
			model.addAttribute("balance", balance);
		}
		
		 List<AccountDto> findAllAccounts = accountsService.findAllAccounts();
		 model.addAttribute("accountsList", findAllAccounts);
        return "transactions"; 
    }
	
	@GetMapping("/transactions")
    public String getTransaction(Model model, @RequestParam(value = "account-number", required = true) Long accountNumber) {
		
		AccountDto account = accountsService.findAccountByAccountNumber(accountNumber);
		
		if(account != null){
			
			setDisplayAmount(account.getTransactions());
			
					
			model.addAttribute("transactions",  account.getTransactions());
			model.addAttribute("account", account);
			String balance = Convert.formatMinorDenominationAmount(String.valueOf(account.getBalance()), 2, false, "\u00A3");
			
			model.addAttribute("balance", balance);
		}

		 List<AccountDto> findAllAccounts = accountsService.findAllAccounts();
		 model.addAttribute("accountsForm", new AccountsForm());
		 model.addAttribute("accountsList", findAllAccounts);
		
		    
        return "transactions"; 
	}
	
	private void setDisplayAmount(List<TransactionsDto> transactions) {
		for(TransactionsDto transaction : transactions) {
			
			transaction.setDisplayAmount(Convert.formatMinorDenominationAmount(String.valueOf(transaction.getAmount()), 2, false, "\u00A3"));
			
		}
	}
	
	
}
