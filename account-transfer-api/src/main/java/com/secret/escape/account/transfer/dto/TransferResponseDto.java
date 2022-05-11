package com.secret.escape.account.transfer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponseDto {
	
	@JsonProperty("from_account")
	private AccountDto fromAccount;
	
	@JsonProperty("to_account")
	private AccountDto toAccount;
	 
}
