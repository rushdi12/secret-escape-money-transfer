package com.secret.escape.account.transfer.ui.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {


	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("account_number")
	private String accountNumber;
	
	@JsonProperty("account_holder_name")
	private String accountHolderName;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("balance")
	private Long balance; 
	
	@JsonProperty("display_balance")
	private String displayBalance; 
	
	@JsonProperty("transactions")
	private List<TransactionsDto> transactions; 
}
