package com.secret.escape.account.transfer.ui.dto;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmailRequestDto {
	
	@JsonProperty("account_number")
	@Length(min=1, max=20, message = "error.account.number)")
	private String accountNumber;
	
	@JsonProperty("email")
	@Length(min=1, max=50, message = "error.account.email")
	@Email
	private String email;
	 
	
}
