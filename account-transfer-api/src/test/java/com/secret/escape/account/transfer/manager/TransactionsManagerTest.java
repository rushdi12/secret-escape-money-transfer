package com.secret.escape.account.transfer.manager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.secret.escape.account.transfer.dto.TransactionsDto;
import com.secret.escape.account.transfer.model.Account;
import com.secret.escape.account.transfer.model.Transaction;
import com.secret.escape.account.transfer.repository.TransactionsRepository;
import com.secret.escape.account.transfer.util.MappingUtil;


@SpringBootTest
public class TransactionsManagerTest {

	@InjectMocks 
    private TransactionsManager transactionsManager = new TransactionsManagerImpl();
	
	@Mock
	private TransactionsRepository transactionsRepository;

	@Mock
	private MappingUtil mappingUtil;

	// Account 1 details
	public static Long account_one_id = 10L;
	public static String account_one_accountNumber = "523231122";
	public static String account_one_accountHolderName = "Mr Rushdi Hoosain";
	public static String account_one_email = "rushdi12@gmail.com";
	public static Long account_one_balance = 20000l;
 
	private static Account accountOne;  
	
	private static TransactionsDto transactionOneDto;
	
	private static Set<Transaction> account_one_transactions;
	private static Transaction transaction;
	
	private static Long transaction_one_id = 1l;
	private static Long transaction_one_amount = 5000l;
	private static String transaction_one_from_account = "523231122";
	private static String transaction_one_to_account_number = "315106044";
 
	 
	
	@BeforeEach
	private void setup() {

		// buildmockdb pbject
		accountOne = new Account(account_one_id, //
				 account_one_accountNumber,//
				 account_one_accountHolderName,//
				 account_one_email, //
				 account_one_balance, //
				 account_one_transactions);
		
		account_one_transactions = new HashSet<Transaction>();
		transaction = new Transaction();

		transaction.setId(transaction_one_id);
		transaction.setAmount(transaction_one_amount);
		transaction.setFromAccount(accountOne);
		transaction.setToAccountNumber(account_one_accountNumber);
		
		account_one_transactions.add(transaction);
		

		// mapping mock transaction 1
		transactionOneDto = new TransactionsDto(
				transaction_one_id, //
				transaction_one_amount, //
				 transaction_one_from_account, //
				transaction_one_to_account_number);
 
	}
 
	@Test
	public void givenAccountNumber_whenGettingTransactions_thenReturnTransactionList() {

		Set<Transaction> transactionList = new HashSet<Transaction>();
		transactionList.add(transaction); 
  

		List<TransactionsDto> transactionListDto = new ArrayList<TransactionsDto>();
		transactionListDto.add(transactionOneDto);
		

		Mockito.when(transactionsRepository.findTransactionByFromAccountNumber(account_one_accountNumber)).thenReturn(transactionList);
		Mockito.when(mappingUtil.buildTransactionDetails(transactionList)).thenReturn(transactionListDto);

		List<TransactionsDto> transactionsByAccountNumber = transactionsManager.getTransactionsByAccountNumber(account_one_accountNumber);

		assertEquals(1, transactionsByAccountNumber.size());

		verify(transactionsRepository, times(1)).findTransactionByFromAccountNumber(account_one_accountNumber);
	}
		
	@Test
	public void givenInvalidAccountNumber_whenGettingTransactions_thenReturnEmptyList() {

		Set<Transaction> transactionList = new HashSet<Transaction>();
		transactionList.add(transaction); 
  

		List<TransactionsDto> transactionListDto = new ArrayList<TransactionsDto>();
		transactionListDto.add(transactionOneDto);
		

		Mockito.when(transactionsRepository.findTransactionByFromAccountNumber(account_one_accountNumber)).thenReturn(new HashSet<Transaction>());
		Mockito.when(mappingUtil.buildTransactionDetails(transactionList)).thenReturn(transactionListDto);

		List<TransactionsDto> transactionsByAccountNumber = transactionsManager.getTransactionsByAccountNumber(account_one_accountNumber);

		assertEquals(0, transactionsByAccountNumber.size());

		verify(transactionsRepository, times(1)).findTransactionByFromAccountNumber(account_one_accountNumber);
	}
		
	@Test
	public void givenTransactiontId_whenGettingTransactions_thenReturnTransaction() {

		Optional<Transaction> optionalTransaction = Optional.of(transaction);


		Mockito.when(transactionsRepository.findById(transaction.getId())).thenReturn(optionalTransaction);
		Mockito.when(mappingUtil.mapTransactionDetails(transaction)).thenReturn(transactionOneDto);

		//call accountManager
		TransactionsDto transactionsResult = transactionsManager.getTransactionsById(transaction.getId());
 
		assertEquals(transaction.getFromAccount().getAccountNumber(), transactionsResult.getFromAccountNumber());
 
	}

	

 

}
