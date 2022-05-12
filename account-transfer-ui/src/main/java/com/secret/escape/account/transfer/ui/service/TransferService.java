package com.secret.escape.account.transfer.ui.service;

import com.secret.escape.account.transfer.ui.dto.TransferRequestDto;
import com.secret.escape.account.transfer.ui.dto.TransferResponseDto;

public interface TransferService {
	
	public TransferResponseDto trransferFunds(TransferRequestDto transferRequestDto);

}
