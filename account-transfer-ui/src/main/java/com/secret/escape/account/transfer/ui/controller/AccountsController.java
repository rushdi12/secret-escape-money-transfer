package com.secret.escape.account.transfer.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.secret.escape.account.transfer.ui.dto.AccountDto;
import com.secret.escape.account.transfer.ui.service.AccountsService;
import com.secret.escape.account.transfer.ui.util.Convert;

@Controller
public class AccountsController {

	@Autowired
	private AccountsService accounstService;
   
	@GetMapping("/")
    public String showAccountsHome(Model model) {
		List<AccountDto> findAllAccounts = accounstService.findAllAccounts();

		setDisplayAmount(findAllAccounts);
		model.addAttribute("accounts", findAllAccounts);	     
        return "accounts"; 
    }
	
	@GetMapping("/accounts")
	public String showAccounts(Model model) {
		List<AccountDto> findAllAccounts = accounstService.findAllAccounts();

		setDisplayAmount(findAllAccounts);
		model.addAttribute("accounts", findAllAccounts);

		return "accounts";
	}
	 
	@GetMapping("/account/edit")
    public String updateEmail(Model model, @RequestParam(value = "email", required = true) String email,
    									   @RequestParam(value = "accountNumber", required = true) String accountNumber) {
		
		accounstService.updateAccountEmail(accountNumber, email);
		model.addAttribute("accounts", accounstService.findAllAccounts());
		     
        return "redirect:/"; 
    }

	private void setDisplayAmount(	List<AccountDto> accounts) {
		for(AccountDto account : accounts) {
			account.setDisplayBalance(Convert.formatMinorDenominationAmount(String.valueOf(account.getBalance()), 2, false, "\u00A3"));
					
		}
	}

}
