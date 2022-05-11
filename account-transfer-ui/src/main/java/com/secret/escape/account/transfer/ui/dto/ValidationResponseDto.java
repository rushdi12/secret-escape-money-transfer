package com.secret.escape.account.transfer.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResponseDto {
	 
	private Boolean isValid;	

	private String errorMessage;;
	 
}
