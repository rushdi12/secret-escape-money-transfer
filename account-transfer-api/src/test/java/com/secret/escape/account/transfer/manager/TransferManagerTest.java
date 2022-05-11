package com.secret.escape.account.transfer.manager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import com.secret.escape.account.transfer.dto.AccountDto;
import com.secret.escape.account.transfer.dto.TransactionsDto;
import com.secret.escape.account.transfer.dto.TransferRequestDto;
import com.secret.escape.account.transfer.exception.BadRequestException;
import com.secret.escape.account.transfer.model.Account;
import com.secret.escape.account.transfer.model.Transaction;
import com.secret.escape.account.transfer.repository.AccountsRepository;
import com.secret.escape.account.transfer.util.MappingUtil;


@SpringBootTest
public class TransferManagerTest {

	@InjectMocks 
    private TransferManager transferManager = new TransferManagerImpl();
	
	@Mock
	private AccountsRepository accountsRepository;

	@Mock
	private MappingUtil mappingUtil;

	@Mock
	private ApplicationEventPublisher applicationEventPublisher;
	
	// Account 1 details
	public static Long account_one_id = 1L;
	public static String account_one_accountNumber = "523231122";
	public static String account_one_accountHolderName = "Mr Rushdi Hoosain";
	public static String account_one_email = "rushdi12@gmail.com";
	public static Long account_one_balance = 20000l;

	// Account 2 details
	public static Long account_two_id = 12L;
	public static String account_two_accountNumber = "315106044";
	public static String account_two_accountHolderName = "Mrs In debt";
	public static String account_two_email = "indebt@gmail.com";
	public static Long account_two_balance = 20000l;

	private static Account accountOne; 
	private static Account accountTwo;
	
	private static AccountDto accountOneDto;
	private static AccountDto accountTwoDto;
	
	@BeforeEach
	private void setup() {
		Set<Transaction> transactions = new HashSet<Transaction>();

		// DB mock account 1 
		accountOne = new Account(account_one_id, //
								 account_one_accountNumber,//
								 account_one_accountHolderName,//
								 account_one_email, //
								 account_one_balance, //
								 transactions);

		// DB mock account 2
		accountTwo = new Account(account_two_id, //
								account_two_accountNumber, //
								account_two_accountHolderName, //
								account_two_email, //
								account_two_balance, //
								transactions);
		
		// mapping mock account 1
		accountOneDto = new AccountDto(account_one_id, //
										account_one_accountNumber, //
										account_one_accountHolderName, //
										account_one_email, //
										account_one_balance, //
										new ArrayList<TransactionsDto>());

		// mapping mock account 2
		accountTwoDto = new AccountDto(account_two_id, //
										account_one_accountNumber, //
										account_one_accountHolderName,//
										account_one_email, //
										account_one_balance, //
										new ArrayList<TransactionsDto>());
		 

	}
 
	@Test
	public void givenAValidTransferRequest_whenPerformingATransfer_thenCompleteTransfer() throws Exception {

		setupMocks();
		
		TransferRequestDto transferRequest = new TransferRequestDto();
		transferRequest.setAmount(1000l);
		transferRequest.setFromAccountNumber(accountOne.getAccountNumber());
		transferRequest.setToAccountNumber(accountTwo.getAccountNumber());
		
		transferManager.performAccountTransfer(transferRequest);

		Long expectedAccountOneBalance = 19000L;
		Long expectedAccountTwoBalance = 21000L;
		
		Account updatedAccountOne = accountsRepository.findByAccountNumber(accountOne.getAccountNumber());
		Account updatedAccountTwo = accountsRepository.findByAccountNumber(accountTwo.getAccountNumber());
		
		assertEquals(String.valueOf(expectedAccountOneBalance), String.valueOf(updatedAccountOne.getBalance()));
		assertEquals(String.valueOf(expectedAccountTwoBalance), String.valueOf(updatedAccountTwo.getBalance()));

	}
	
	@Test
	public void givenAnInsufficientFundsTransferRequest_whenPerformingATransfer_thenCancelTransfer() {

		setupMocks(); 
		
		TransferRequestDto transferRequest = new TransferRequestDto();
		transferRequest.setAmount(1000000l);
		transferRequest.setFromAccountNumber(accountOne.getAccountNumber());
		transferRequest.setToAccountNumber(accountTwo.getAccountNumber());
		
		BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () -> {
			transferManager.performAccountTransfer(transferRequest);
			 
		}, "BadRequestException was expected");
		
		Assertions.assertEquals("Insufficient funds available [" + accountOne.getBalance() + "]", thrown.getMessage());
		
		Long expectedAccountOneBalance = 20000L;
		Long expectedAccountTwoBalance = 20000L;
		
		Account updatedAccountOne = accountsRepository.findByAccountNumber(accountOne.getAccountNumber());
		Account updatedAccountTwo = accountsRepository.findByAccountNumber(accountTwo.getAccountNumber());
		
		assertEquals(String.valueOf(expectedAccountOneBalance), String.valueOf(updatedAccountOne.getBalance()));
		assertEquals(String.valueOf(expectedAccountTwoBalance), String.valueOf(updatedAccountTwo.getBalance()));

	}
	
	@Test
	public void givenTwoSameAccountNumbers_whenPerformingATransfer_thenCancelTransfer() {
		
		setupMocks(); 
		
		TransferRequestDto transferRequest = new TransferRequestDto();
		transferRequest.setAmount(1000000l);
		transferRequest.setFromAccountNumber(accountOne.getAccountNumber());
		transferRequest.setToAccountNumber(accountOne.getAccountNumber());
		
		
		BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () -> {
			transferManager.performAccountTransfer(transferRequest);
			 
		}, "BadRequestException was expected");
		
		Assertions.assertEquals("Accounts cannot be the same [" + transferRequest.getFromAccountNumber() + "]", thrown.getMessage());
		
		Long expectedAccountOneBalance = 20000L;
		Long expectedAccountTwoBalance = 20000L;
		
		Account updatedAccountOne = accountsRepository.findByAccountNumber(accountOne.getAccountNumber());
		Account updatedAccountTwo = accountsRepository.findByAccountNumber(accountTwo.getAccountNumber());
		
		assertEquals(String.valueOf(expectedAccountOneBalance), String.valueOf(updatedAccountOne.getBalance()));
		assertEquals(String.valueOf(expectedAccountTwoBalance), String.valueOf(updatedAccountTwo.getBalance()));

	}
	
	@Test
	public void givenInvalidFromAccountNumberForTransferRequest_whenPerformingATransfer_thenCancelTransfer() {

		setupMocks(); 
		
		TransferRequestDto transferRequest = new TransferRequestDto();
		transferRequest.setAmount(1000000l);
		transferRequest.setFromAccountNumber("000000000000");
		transferRequest.setToAccountNumber(accountTwo.getAccountNumber());
		
		
		BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () -> {
			transferManager.performAccountTransfer(transferRequest);
			 
		}, "BadRequestException was expected");
		
		Assertions.assertEquals("Account [" + transferRequest.getFromAccountNumber() + "] does not exist", thrown.getMessage());
		
		Long expectedAccountOneBalance = 20000L;
		Long expectedAccountTwoBalance = 20000L;
		
		Account updatedAccountOne = accountsRepository.findByAccountNumber(accountOne.getAccountNumber());
		Account updatedAccountTwo = accountsRepository.findByAccountNumber(accountTwo.getAccountNumber());
		
		assertEquals(String.valueOf(expectedAccountOneBalance), String.valueOf(updatedAccountOne.getBalance()));
		assertEquals(String.valueOf(expectedAccountTwoBalance), String.valueOf(updatedAccountTwo.getBalance()));

	}

	private void setupMocks() {
		Mockito.when(accountsRepository.findByAccountNumber(accountOne.getAccountNumber())).thenReturn(accountOne);
		Mockito.when(accountsRepository.findByAccountNumber(accountTwo.getAccountNumber())).thenReturn(accountTwo);
		
		Mockito.when(accountsRepository.save(accountOne)).thenReturn(accountOne);
		Mockito.when(accountsRepository.save(accountTwo)).thenReturn(accountTwo);
 
		Mockito.when(mappingUtil.mapAccountDetails(accountOne, true)).thenReturn(accountOneDto);
		Mockito.when(mappingUtil.mapAccountDetails(accountTwo, true)).thenReturn(accountTwoDto);
		
		mock(ApplicationEventPublisher.class);
	}
	
}
