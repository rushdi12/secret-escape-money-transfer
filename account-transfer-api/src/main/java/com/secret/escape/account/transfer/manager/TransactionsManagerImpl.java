package com.secret.escape.account.transfer.manager;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.secret.escape.account.transfer.dto.TransactionsDto;
import com.secret.escape.account.transfer.model.Transaction;
import com.secret.escape.account.transfer.repository.TransactionsRepository;
import com.secret.escape.account.transfer.util.MappingUtil;

@Service
public class TransactionsManagerImpl implements TransactionsManager{
	
	@Autowired
	private TransactionsRepository transactionsRepo;
	
	@Autowired
	private MappingUtil mappingUtil;
	
	@Override
	@Transactional
	public List<TransactionsDto> getTransactionsByAccountId(Long accountId) {

		Set<Transaction> transactions = transactionsRepo.findByFromAccountId(accountId);
		return mappingUtil.buildTransactionDetails(transactions);

	}
	
	@Override
	@Transactional
	public TransactionsDto getTransactionsById(Long id) {

		TransactionsDto transactionDetails = null; 
		Optional<Transaction> transaction = transactionsRepo.findById(id);
		
		if(transaction.isPresent()) {
			 transactionDetails = mappingUtil.mapTransactionDetails(transaction.get());
			
		}
		
		return transactionDetails;
	}

	@Override
	public List<TransactionsDto> getTransactionsByAccountNumber(String accountNumber) {
		Set<Transaction> transactions = transactionsRepo.findTransactionByFromAccountNumber(accountNumber);
		return mappingUtil.buildTransactionDetails(transactions);
	}

}
