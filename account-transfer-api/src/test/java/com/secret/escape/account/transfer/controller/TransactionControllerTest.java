package com.secret.escape.account.transfer.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.escape.account.transfer.dto.TransactionsDto;
import com.secret.escape.account.transfer.dto.TransactionsRequestDto;
import com.secret.escape.account.transfer.manager.TransactionsManager;
import com.secret.escape.account.transfer.model.Account;
import com.secret.escape.account.transfer.model.Transaction;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private TransactionsController transactionsController = new TransactionsController();
	
	@MockBean
	private TransactionsManager transactionsManager;
 
	
	private static Account accountOne;   
	private static Set<Transaction> account_one_transactions;
	private static Transaction transaction;
	
	// Account 1 details
	public static Long account_one_id = 10L;
	public static String account_one_accountNumber = "523231122";
	public static String account_one_accountHolderName = "Mr Rushdi Hoosain";
	public static String account_one_email = "rushdi12@gmail.com";
	public static Long account_one_balance = 20000l;
 
	private static Long transaction_one_id = 1L;
	private static Long transaction_one_amount = 5000l;
	private static String transaction_one_from_account = "523231122";
	private static String transaction_one_to_account_number = "315106044";
	private static TransactionsDto transactionOneDto;
	
	@BeforeEach
	private void setup() {

		// build mock db object
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
	public void givenAnTransactionId_whenGettingTransactions_thenReturnList() throws Exception {

		
		Set<Transaction> transactionList = new HashSet<Transaction>();
		transactionList.add(transaction); 

		List<TransactionsDto> transactionListDto = new ArrayList<TransactionsDto>();
		transactionListDto.add(transactionOneDto);
		
		Mockito.when(transactionsManager.getTransactionsById(transaction.getId()))
			   .thenReturn(transactionOneDto);
		
		 mockMvc
	        .perform(MockMvcRequestBuilders.get("/api/v1/transactions/{id}", transaction.getId()))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.id", equalTo(transactionOneDto.getId().intValue())));
	  
	}
	
	@Test
	public void givenAnAccountNumber_whenGettingTransactions_thenReturnList() throws Exception {

		List<TransactionsDto> transactionListDto = new ArrayList<TransactionsDto>();
		transactionListDto.add(transactionOneDto);
		
		
		Mockito.when(transactionsManager.getTransactionsByAccountNumber(account_one_accountNumber))
		   .thenReturn(transactionListDto);
	
		TransactionsRequestDto transactionsRequestDto = new TransactionsRequestDto();
		transactionsRequestDto.setAccountNumber(account_one_accountNumber);
		
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transactions/account")
				.content(mapToJson(transactionsRequestDto))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
	}
 
	
	public String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		 
		String writeValueAsString = objectMapper.writeValueAsString(obj);
		return writeValueAsString;
	}
	
}
