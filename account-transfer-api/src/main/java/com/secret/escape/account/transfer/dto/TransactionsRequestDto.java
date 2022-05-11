package com.secret.escape.account.transfer.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsRequestDto {

	@JsonProperty("account_number")
	@NotEmpty
	private String accountNumber;
	
}
