package com.secret.escape.account.transfer.manager;

import com.secret.escape.account.transfer.dto.TransferRequestDto;
import com.secret.escape.account.transfer.dto.TransferResponseDto;

public interface TransferManager {

	public TransferResponseDto performAccountTransfer(TransferRequestDto transferRequest) throws Exception;

}
