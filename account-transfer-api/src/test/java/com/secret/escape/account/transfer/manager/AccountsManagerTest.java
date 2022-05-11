package com.secret.escape.account.transfer.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

import com.secret.escape.account.transfer.dto.AccountCreateRequestDto;
import com.secret.escape.account.transfer.dto.AccountDto;
import com.secret.escape.account.transfer.dto.TransactionsDto;
import com.secret.escape.account.transfer.model.Account;
import com.secret.escape.account.transfer.model.Transaction;
import com.secret.escape.account.transfer.repository.AccountsRepository;
import com.secret.escape.account.transfer.util.MappingUtil;


@SpringBootTest
public class AccountsManagerTest {

	@InjectMocks 
    private AccountsManager accountsManager = new AccountsManagerImpl();
	
	@Mock
	private AccountsRepository accountsRepository;

	@Mock
	private MappingUtil mappingUtil;

	// Account 1 details
	public static Long account_one_id = 10L;
	public static String account_one_accountNumber = "523231122";
	public static String account_one_accountHolderName = "Mr Rushdi Hoosain";
	public static String account_one_email = "rushdi12@gmail.com";
	public static Long account_one_balance = 20000l;

	// Account 2 details
	public static Long account_two_id = 11L;
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
	public void whenGettingAllAccounts_thenReturnAccountList() {

		List<Account> accountList = new ArrayList<Account>();
		accountList.add(accountOne);
		accountList.add(accountTwo);

		List<AccountDto> accountListDto = new ArrayList<AccountDto>();
		accountListDto.add(accountOneDto);
		accountListDto.add(accountTwoDto);

		Mockito.when(accountsRepository.findAll()).thenReturn(accountList);
		Mockito.when(mappingUtil.buildAccountDetails(accountList, false)).thenReturn(accountListDto);
		
		List<AccountDto> getAccountResult = accountsManager.getAllAccounts();

		assertEquals(2, getAccountResult.size());

		verify(accountsRepository, times(1)).findAll();

	}
	
	@Test
	public void givenAccountId_whenGettingAnAccount_thenReturnAccount() {

		Optional<Account> accountOption = Optional.of(accountOne);

		Mockito.when(accountsRepository.findById(accountOne.getId())).thenReturn(accountOption);
		Mockito.when(mappingUtil.mapAccountDetails(accountOne, true)).thenReturn(accountOneDto);

		//call accountManager
		AccountDto accountResult = accountsManager.getAccountById(accountOne.getId(), true);

		assertEquals(accountOne.getId(), accountResult.getId());

		verify(accountsRepository, times(1)).findById(accountOne.getId());

	}
	
	@Test
	public void givenAccountNumber_whenGettingaAccount_thenReturnAccount() throws Exception {

		Mockito.when(accountsRepository.findByAccountNumber(account_one_accountNumber)).thenReturn(accountOne);
		Mockito.when(mappingUtil.mapAccountDetails(accountOne, true)).thenReturn(accountOneDto);

		//call accountManager
		AccountDto accountResult = accountsManager.getAccountByNumber(account_one_accountNumber, true);

		assertEquals(account_one_accountNumber,  accountResult.getAccountNumber());
		
		//verify result
		verify(accountsRepository, times(1)).findByAccountNumber(account_one_accountNumber);

	}
	
	
	@Test
	public void givenAnIncorrectAccountNumber_whenGettingaAccount_thenReturnEmpty() throws Exception {
		
		// return a different mock account
		Mockito.when(accountsRepository.findByAccountNumber(account_one_accountNumber)).thenReturn(accountTwo);
		Mockito.when(mappingUtil.mapAccountDetails(accountOne, true)).thenReturn(accountOneDto);

		//call accountManager
		AccountDto accountResult = accountsManager.getAccountByNumber(account_one_accountNumber, true);
		assertTrue(accountResult == null);
		//verify result
		verify(accountsRepository, times(1)).findByAccountNumber(account_one_accountNumber);

	}

	@Test
	public void givenNewAccount_whenSavingAccount_thenVerifyNewAccount() throws Exception {

		Mockito.when(accountsRepository.save(accountOne)).thenReturn(accountOne);
		Mockito.when(accountsRepository.findByAccountNumber(account_one_accountNumber)).thenReturn(accountOne);
		Mockito.when(mappingUtil.mapAccountDetails(accountOne, true)).thenReturn(accountOneDto);

		//call accountManager
		AccountCreateRequestDto accountCreateRequestDto = new AccountCreateRequestDto(account_one_accountNumber, account_one_accountHolderName, account_one_email, account_one_balance);
		accountsManager.createAccount(accountCreateRequestDto);

		//verify account creation
		AccountDto accountResult = accountsManager.getAccountByNumber(account_one_accountNumber, true);
				
		assertEquals(account_one_accountNumber, accountResult.getAccountNumber());
	}

	@Test
	public void givenUpdatedAccount_whenUpdatingAccount_thenVerifyUpdatedAccount() throws Exception {

	 
		Mockito.when(accountsRepository.save(accountOne)).thenReturn(accountOne);
		Mockito.when(accountsRepository.findByAccountNumber(account_one_accountNumber)).thenReturn(accountOne);
		Mockito.when(mappingUtil.mapAccountDetails(accountOne, true)).thenReturn(accountOneDto);

		//call accountManager
		AccountCreateRequestDto accountCreateRequestDto = new AccountCreateRequestDto(account_one_accountNumber, account_one_accountHolderName, account_one_email, account_one_balance);
		accountsManager.createAccount(accountCreateRequestDto);

		//verify account creation
		AccountDto accountResult = accountsManager.getAccountByNumber(account_one_accountNumber, true);
				
		assertEquals(account_one_accountNumber, accountResult.getAccountNumber());

		
	}
	

}
