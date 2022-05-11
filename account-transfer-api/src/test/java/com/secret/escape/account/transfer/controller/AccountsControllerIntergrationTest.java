package com.secret.escape.account.transfer.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.secret.escape.account.transfer.dto.AccountCreateRequestDto;
import com.secret.escape.account.transfer.model.Account;
import com.secret.escape.account.transfer.repository.AccountsRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountsControllerIntergrationTest extends AbstractTestController  {

	@Autowired 
	private AccountsRepository repository;
	
	@Test
	public void givenAnEmptyRequest_whenGettingAllAccounts_thenReturnSuccess() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/accounts/all").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize((int) repository.count())));

	}
	
	@Test
	public void givenAnAccountId_whenGettingAccount_thenReturnSuccess() throws Exception {

		 mockMvc
	        .perform(MockMvcRequestBuilders.get("/api/v1/accounts/{id}", 1L))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("id", equalTo(1)));
	       
	 
	}
	
	@Test
	public void givenAnAccountNumber_whenGettingAccount_thenReturnSuccess() throws Exception {

		 mockMvc
	        .perform(MockMvcRequestBuilders.get("/api/v1/accounts/account-number/{account-number}", account_one_accountNumber))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("account_number", equalTo(account_one_accountNumber)));
	       
	}
	
	@Test
	public void givenAnInvalidAccountNumber_whenGettingAccount_thenReturnBadRequest() throws Exception {

		 mockMvc
	        .perform(MockMvcRequestBuilders.get("/api/v1/accounts/account-number/{account-number}", "090393"))
	        .andExpect(status().isBadRequest());
	 
	}
	
	@Test
	public void givenAnAccountCreationRequest_whenCreatingAccount_thenReturnSuccess() throws Exception {

		AccountCreateRequestDto request = new AccountCreateRequestDto();
		request.setAccountHolderName("Rushdi Hoosain");
		request.setAccountNumber("2468101212");
		request.setEmail("rushdi12@gmail.com");
		request.setBalance(2000l);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts/create")
				.content(mapToJson(request))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	 
	}
	
	@Test
	public void givenAnAccountNumber_whenDeletingAccount_thenReturnSuccess() throws Exception {

		 mockMvc
	        .perform(MockMvcRequestBuilders.delete("/api/v1/accounts/delete/{id}", "31510604"))
	        .andExpect(status().isOk());
	       
	 
	}
	
	@Test
	public void givenUpdateAccountRequest_whenUpdatingAnAccount_thenReturnSuccess() throws Exception {

		AccountCreateRequestDto request = new AccountCreateRequestDto();
		request.setAccountHolderName("Rushdi Hoosain");
		request.setAccountNumber("52323112");
		request.setEmail("newRushdi12@gmail.com");
		request.setBalance(2000l);
		
		 mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/accounts/update")
					.content(mapToJson(request))
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		 
		 Account account = repository.findByAccountNumber("52323112");
		 String actual = account.getEmail();
		 
		 assertEquals("newRushdi12@gmail.com", actual);
	}
	
	
	
}
