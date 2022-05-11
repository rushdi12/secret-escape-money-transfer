package com.secret.escape.account.transfer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class EmailEventDto {
	
	private String amount;
	
	private String email;
	
	private String accountNumber;
	
	private String balance;
	

}
