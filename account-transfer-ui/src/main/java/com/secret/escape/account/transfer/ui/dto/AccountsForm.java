package com.secret.escape.account.transfer.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountsForm {

	private Long id; 
	
	private Long accountNumber;
	
	private String accountHolderName;
	
	private String email;
	
	private Long balance; 
 
}
