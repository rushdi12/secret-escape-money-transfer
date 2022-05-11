package com.secret.escape.account.transfer.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsRequestDto {

	@JsonProperty("account_number")
	private String accountNumber;
	
}
