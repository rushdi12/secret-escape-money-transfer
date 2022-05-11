package com.secret.escape.account.transfer.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import com.secret.escape.account.transfer.dto.AccountCreateRequestDto;
import com.secret.escape.account.transfer.dto.AccountDto;
import com.secret.escape.account.transfer.dto.TransactionsDto;
import com.secret.escape.account.transfer.exception.BadRequestException;
import com.secret.escape.account.transfer.manager.AccountsManager;
import com.secret.escape.account.transfer.repository.AccountsRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private AccountsController accountsContoller = new AccountsController();
	
	@MockBean
	private AccountsManager accountsManager;

	@Autowired
	private AccountsRepository accountsRepository; 
	
	
	private static AccountDto accountOneDto;
	private static AccountDto accountTwoDto;
	
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
	
	@BeforeEach
	private void setup() {

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
	public void givenAnEmptyRequest_whenGettingAllAccounts_thenReturnSuccess() throws Exception {

		List<AccountDto> accountList = new ArrayList<AccountDto>();
		accountList.add(accountOneDto);
		accountList.add(accountTwoDto);
		
		Mockito.when(accountsManager.getAllAccounts())
		       .thenReturn(accountList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/accounts/all").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
				
	}
	
	
	@Test
	public void givenAnAccountId_whenGettingAccount_thenReturnSuccess() throws Exception {

		Mockito.when(accountsManager.getAccountById(account_one_id, true))
			   .thenReturn(accountOneDto);
		
		 mockMvc
	        .perform(MockMvcRequestBuilders.get("/api/v1/accounts/{id}", account_one_id))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.account_number", equalTo(account_one_accountNumber)));
	 
	}
	
	@Test
	public void givenAnAccountNumber_whenGettingAccount_thenReturnSuccess() throws Exception {

		Mockito.when(accountsManager.getAccountByNumber(account_one_accountNumber, true))
		   .thenReturn(accountOneDto);
	
		 mockMvc
	        .perform(MockMvcRequestBuilders.get("/api/v1/accounts/account-number/{account-number}", account_one_accountNumber))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("account_number", equalTo(account_one_accountNumber)));
	       
	}
	
	@Test
	public void givenAnInvalidAccountNumber_whenGettingAccount_thenReturnBadRequest() throws Exception {

		Mockito.when(accountsManager.getAccountByNumber("090393", true))
		   .thenThrow(new BadRequestException("Invalid account number"));

		 mockMvc
	        .perform(MockMvcRequestBuilders.get("/api/v1/accounts/account-number/{account-number}", "090393"))
	        .andExpect(status().isBadRequest());
		       
	}
	
	@Test
	public void givenAnAccountCreationRequest_whenCreatingAccount_thenReturnSuccess() throws Exception {

		AccountCreateRequestDto request = new AccountCreateRequestDto();
		request.setAccountHolderName("Rushdi Hoosain");
		request.setAccountNumber("24681012");
		request.setEmail("rushdi12@gmail.com");
		request.setBalance(2000l);
		
		Mockito.doNothing().when(accountsManager).createAccount(request);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts/create")
				.content(mapToJson(request))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	  
	}
	
	@Test
	public void givenAnAccountNumber_whenDeletingAccount_thenReturnSuccess() throws Exception {

		Mockito.doNothing().when(accountsManager).deleteAccount(account_one_accountNumber);
		
		 mockMvc
	        .perform(MockMvcRequestBuilders.delete("/api/v1/accounts/delete/{id}", "31510604"))
	        .andExpect(status().isOk());
	 
	}
	
	@Test
	public void givenUpdateAccountRequest_whenUpdatingAnAccount_thenReturnSuccess() throws Exception {

		AccountCreateRequestDto request = new AccountCreateRequestDto();
		request.setAccountHolderName("Rushdi Hoosain");
		request.setAccountNumber("24681012");
		request.setEmail("newRushdi12@gmail.com");
		request.setBalance(2000l);
 
		// mapping mock account 1
		accountOneDto = new AccountDto(account_one_id, //
										"24681012", //
										"Rushdi Hoosain", //
										"newRushdi12@gmail.com", //
										 2000l, //
 										 new ArrayList<TransactionsDto>());
				
		Mockito.when(accountsManager.updateAccount(request))
		   .thenReturn(accountOneDto);
		
	       
		 mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/accounts/update")
					.content(mapToJson(request))
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("email", equalTo("newRushdi12@gmail.com")));
	}
	
	public String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		 
		String writeValueAsString = objectMapper.writeValueAsString(obj);
		return writeValueAsString;
	}
	
}
