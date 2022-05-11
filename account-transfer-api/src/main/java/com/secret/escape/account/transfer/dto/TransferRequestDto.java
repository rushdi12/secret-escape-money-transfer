package com.secret.escape.account.transfer.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDto {
	
	@JsonProperty("amount")
	@NotNull 
	@Min(value = 1L, message = "amount.greater.than.zero")
	private Long amount;
	
	@JsonProperty("from_account_number")
	@Length(min=1, max=20, message = "error.account.number")
	@NotNull
	@NotEmpty
	private String fromAccountNumber;
	
	@JsonProperty("to_account_number")
	@NotNull
	@NotEmpty
	@Length(min=1, max=20, message = "error.account.number")
	private String toAccountNumber;
}
