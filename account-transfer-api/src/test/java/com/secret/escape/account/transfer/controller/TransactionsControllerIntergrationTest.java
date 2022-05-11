package com.secret.escape.account.transfer.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TransactionsControllerIntergrationTest extends AbstractTestController {
	
	@Test
	public void givenAnTransactionId_whenGettingTransaction_thenReturnSuccess() throws Exception {
 	 
		 mockMvc
	        .perform(MockMvcRequestBuilders.get("/api/v1/transactions/{id}", 1))
	        .andExpect(status().isOk()) 
		    .andExpect(jsonPath("id",  equalTo(1)));
	       
	}
		
	@Test
	public void givenAccountId_whenGettingAllTransactionsForAnAccount_thenReturnSuccess() throws Exception {
 	 
		 mockMvc
	        .perform(MockMvcRequestBuilders.get("/api/v1/transactions/account/{id}", 1))
	        .andExpect(status().isOk()) 
		    .andExpect(jsonPath("[0].amount",  equalTo(500)));
	}
	
	@Test
	public void givenAInvalidAccountId_whenGettingAllTransactionsForAnAccount_thenReturnEmptyList() throws Exception {
 	 
		 mockMvc
	        .perform(MockMvcRequestBuilders.get("/api/v1/transactions/account/{id}", 100))
	        .andExpect(status().isOk())  
		    .andExpect(jsonPath("$", hasSize((int) 0)));

	}
	
}
