package com.secret.escape.account.transfer.ui.service;

import com.secret.escape.account.transfer.ui.dto.TransferForm;
import com.secret.escape.account.transfer.ui.dto.ValidationResponseDto;

public interface TransferValidationService {

	public ValidationResponseDto isValidTransfer(TransferForm transferRequest);
	
}
