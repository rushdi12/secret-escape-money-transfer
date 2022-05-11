package com.secret.escape.account.transfer.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.escape.account.transfer.dto.AccountCreateRequestDto;

public class AbstractTestController {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	//Account 1 details
	public static String account_one_accountNumber = "52323112";
	public static String account_one_accountHolderName = "Mr Rushdi Hoosain";
	public static String account_one_email = "rushdi12@gmail.com";
	public static Long account_one_balance = 20000l;
	
	//Account 2 details
	public static String account_two_accountNumber = "31510604";
	public static String account_two_accountHolderName = "Mrs In debt";
	public static String account_two_email = "indebt@gmail.com";
	public static Long account_two_balance = 20000l;
	
	
	@BeforeEach
	public void setupAccounts() throws Exception {

		AccountCreateRequestDto account1 = new AccountCreateRequestDto();
		account1.setAccountHolderName(account_one_accountHolderName);
		account1.setAccountNumber(account_one_accountNumber);
		account1.setEmail(account_one_email);
		account1.setBalance(account_one_balance);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts/create")
				.content(mapToJson(account1))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		AccountCreateRequestDto account2 = new AccountCreateRequestDto();
		account2.setAccountHolderName(account_two_accountHolderName);
		account2.setAccountNumber(account_two_accountNumber);
		account2.setEmail(account_two_email);
		account2.setBalance(account_one_balance);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts/create")
				.content(mapToJson(account2))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		
	}
	
	
	@AfterEach
	public void removeAccount() throws Exception {

		 mockMvc
	        .perform(MockMvcRequestBuilders.delete("/api/v1/accounts/delete/{id}", account_one_accountNumber))
	        .andExpect(status().isOk());
	       

		 mockMvc
	        .perform(MockMvcRequestBuilders.delete("/api/v1/accounts/delete/{id}", account_two_accountNumber))
	        .andExpect(status().isOk());
	       
	}
	

	public String mapToJson(Object obj) throws JsonProcessingException {
		
		String writeValueAsString = objectMapper.writeValueAsString(obj);
		return writeValueAsString;
	}

}
