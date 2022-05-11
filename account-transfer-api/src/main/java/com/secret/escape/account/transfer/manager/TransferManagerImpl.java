package com.secret.escape.account.transfer.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.secret.escape.account.transfer.dto.AccountDto;
import com.secret.escape.account.transfer.dto.EmailEventDto;
import com.secret.escape.account.transfer.dto.TransferRequestDto;
import com.secret.escape.account.transfer.dto.TransferResponseDto;
import com.secret.escape.account.transfer.event.TransferCompletedEvent;
import com.secret.escape.account.transfer.exception.BadRequestException;
import com.secret.escape.account.transfer.model.Account;
import com.secret.escape.account.transfer.model.Transaction;
import com.secret.escape.account.transfer.repository.AccountsRepository;
import com.secret.escape.account.transfer.util.MappingUtil;

@Service
public class TransferManagerImpl implements TransferManager{
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	MappingUtil mappingUtil;
	

	@Autowired
	ApplicationEventPublisher applicationEventPublisher;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public TransferResponseDto performAccountTransfer(TransferRequestDto transferRequest) throws Exception {
		
		TransferResponseDto transferResponseDto = new TransferResponseDto();
		
		validateTransferRequest(transferRequest);
		
		Account fromAccount = accountsRepository.findByAccountNumber(transferRequest.getFromAccountNumber());
		Account toAccount = accountsRepository.findByAccountNumber(transferRequest.getToAccountNumber());
		
		Long transferAmount = transferRequest.getAmount();
		
		Long updatedFromAccountBalance = fromAccount.getBalance() - transferAmount;
		Long updatedToAccountBalance = toAccount.getBalance() + transferAmount;
		
		fromAccount.setBalance(updatedFromAccountBalance);
		toAccount.setBalance(updatedToAccountBalance);
		
		Transaction fromTransaction = new Transaction(); 
		fromTransaction.setAmount(transferAmount);
		fromTransaction.setFromAccount(fromAccount);
		fromTransaction.setToAccountNumber(toAccount.getAccountNumber());
		
		Set<Transaction> fromTransactions = fromAccount.getTransactions();
		fromTransactions.add(fromTransaction);
		fromAccount.setTransactions(fromTransactions);
		
		//update the TO account with a transaction to show history 
		Transaction toTransaction = new Transaction(); 
		toTransaction.setAmount(transferAmount);
		toTransaction.setFromAccount(toAccount);
		toTransaction.setToAccountNumber(fromAccount.getAccountNumber());
		
		Set<Transaction> toTransactions = toAccount.getTransactions();
		toTransactions.add(toTransaction);
		toAccount.setTransactions(toTransactions);
		
		Account updatedFromAccount = accountsRepository.save(fromAccount);
		Account updatedToAccount = accountsRepository.save(toAccount);
		
		AccountDto fromAccountDetailsDto = mappingUtil.mapAccountDetails(updatedFromAccount, true);
		AccountDto toAccountDetailsDto = mappingUtil.mapAccountDetails(updatedToAccount, true);
	 	
		transferResponseDto.setFromAccount(mappingUtil.mapAccountDetails(updatedFromAccount, true));
		transferResponseDto.setToAccount(mappingUtil.mapAccountDetails(updatedToAccount, true));
		
		handleEmailEvent(transferAmount, fromAccountDetailsDto, toAccountDetailsDto);
		
		return transferResponseDto;
	}
	
	private void validateTransferRequest(TransferRequestDto transferRequest) throws Exception{
		
		if(transferRequest.getFromAccountNumber().equalsIgnoreCase(transferRequest.getToAccountNumber())) {
			throw new BadRequestException("Accounts cannot be the same [" + transferRequest.getFromAccountNumber() + "]");
		}
		
		Account fromAccount = accountsRepository.findByAccountNumber(transferRequest.getFromAccountNumber());
		
		if(fromAccount == null) {
			throw new BadRequestException("Account [" + transferRequest.getFromAccountNumber() + "] does not exist");
		}
		
		Account toAccount = accountsRepository.findByAccountNumber(transferRequest.getFromAccountNumber());
		
		
		if(toAccount == null) {
			throw new BadRequestException("Account [" + transferRequest.getToAccountNumber() + "] does not exist");
		}
		
		if(fromAccount.getBalance() < transferRequest.getAmount()){
			throw new BadRequestException("Insufficient funds available [" + fromAccount.getBalance() + "]");
		}
		
	}

	private void handleEmailEvent(Long transferAmount, AccountDto fromAccountDetailsDto,
			AccountDto toAccountDetailsDto) {
		
		EmailEventDto fromEmailEvent = new EmailEventDto(transferAmount.toString(), 
														 fromAccountDetailsDto.getEmail(),
														 fromAccountDetailsDto.getAccountNumber(), 
														 fromAccountDetailsDto.getBalance().toString());
		
		
		EmailEventDto toEmailEvent = new EmailEventDto(transferAmount.toString(), 
													   toAccountDetailsDto.getEmail(),
													   toAccountDetailsDto.getAccountNumber(), 
													   toAccountDetailsDto.getBalance().toString());
	
		
		List<EmailEventDto>  emailEvents = new ArrayList<EmailEventDto>();
		emailEvents.add(fromEmailEvent);
		emailEvents.add(toEmailEvent);
		
		applicationEventPublisher.publishEvent(new TransferCompletedEvent(emailEvents));
		
	}
}
