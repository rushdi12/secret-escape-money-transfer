package com.secret.escape.account.transfer.ui.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AccountsList {

	 private List<AccountDto> accounts;

	    public AccountsList() {
	    	accounts = new ArrayList<>();
	    }
	    
}
