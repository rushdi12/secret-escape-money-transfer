package com.secret.escape.account.transfer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsDto {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("amount")
	private Long amount;
	
	@JsonProperty("from_account_number")
	private String fromAccountNumber;
	
	@JsonProperty("to_account_number")
	private String toAccountNumber;

	
}
