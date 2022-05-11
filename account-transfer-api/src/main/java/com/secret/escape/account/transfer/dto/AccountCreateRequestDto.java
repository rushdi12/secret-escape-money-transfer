package com.secret.escape.account.transfer.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRequestDto {
	
	@JsonProperty("account_number")
	@Length(min=1, max=20, message = "error.account.number)")
	private String accountNumber;
	
	@JsonProperty("account_holder_name")
	@Length(min=1, max=20, message = "error.account.holder.name")
	private String accountHolderName;
	
	@JsonProperty("email")
	@Length(min=1, max=50, message = "error.account.email")
	@Email
	private String email;
	
	@JsonProperty("balance")
	@NotEmpty
	private Long balance;
	
}
