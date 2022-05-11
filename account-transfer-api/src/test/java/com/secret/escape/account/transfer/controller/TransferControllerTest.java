package com.secret.escape.account.transfer.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

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
import com.secret.escape.account.transfer.dto.AccountDto;
import com.secret.escape.account.transfer.dto.TransactionsDto;
import com.secret.escape.account.transfer.dto.TransferRequestDto;
import com.secret.escape.account.transfer.dto.TransferResponseDto;
import com.secret.escape.account.transfer.exception.BadRequestException;
import com.secret.escape.account.transfer.manager.TransferManager;

@SpringBootTest
@AutoConfigureMockMvc
public class TransferControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private TransferController transferController = new TransferController();
	
	@MockBean
	private TransferManager transferManager;
 
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
 
		
		private static AccountDto accountOneDto;
		private static AccountDto accountTwoDto;
		
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
	public void givenAValidTransferRequest_whenPerformingATransfer_thenReturnSuccess() throws Exception {
	 
		TransferRequestDto request = new TransferRequestDto();
		request.setAmount(1000l);
		request.setFromAccountNumber(account_one_accountNumber);
        request.setToAccountNumber(account_two_accountNumber);

		accountOneDto.setBalance(19000L);
		accountTwoDto.setBalance(21000L);
		
		TransferResponseDto transferResponseDto = new TransferResponseDto();
		
		transferResponseDto.setFromAccount(accountOneDto);
		transferResponseDto.setToAccount(accountTwoDto);
		
		Mockito.when(transferManager.performAccountTransfer(request))
			   .thenReturn(transferResponseDto);
		
		Long expectedAccountOneBalance = 19000L;
		Long expectedAccountTwoBalance = 21000L;
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transfer")
				.content(mapToJson(request))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.from_account.balance", equalTo(expectedAccountOneBalance.intValue())))
				.andExpect(jsonPath("$.to_account.balance", equalTo(expectedAccountTwoBalance.intValue())))
				.andReturn();
	}
	
	@Test
	public void givenInsufficientFundsFromAccountTransferRequest_whenPerformingATransfer_thenReturnBadRequest() throws Exception {
	 
		TransferRequestDto request = new TransferRequestDto();
		request.setAmount(5000000l);
		request.setFromAccountNumber(account_one_accountNumber);
        request.setToAccountNumber(account_two_accountNumber);

		accountOneDto.setBalance(19000L);
		accountTwoDto.setBalance(21000L);
		
		TransferResponseDto transferResponseDto = new TransferResponseDto();
		
		transferResponseDto.setFromAccount(accountOneDto);
		transferResponseDto.setToAccount(accountTwoDto);
		
		Mockito.when(transferManager.performAccountTransfer(request))
			   .thenThrow(new BadRequestException("Insufficient funds"));
		 
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transfer")
				.content(mapToJson(request))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
			 
	  
	}
	
	@Test
	public void givenAnEmptyFromAccountTransferRequest_whenPerformingATransfer_thenReturnBadRequest() throws Exception {
	 
		TransferRequestDto request = new TransferRequestDto();
		request.setAmount(1000l);
		request.setFromAccountNumber("");
        request.setToAccountNumber(account_two_accountNumber);

		accountOneDto.setBalance(19000L);
		accountTwoDto.setBalance(21000L);
		
		TransferResponseDto transferResponseDto = new TransferResponseDto();
		
		transferResponseDto.setFromAccount(accountOneDto);
		transferResponseDto.setToAccount(accountTwoDto);
		
		Mockito.when(transferManager.performAccountTransfer(request))
			   .thenReturn(transferResponseDto);
		 
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transfer")
				.content(mapToJson(request))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
			 
	  
	}
	
	
	public String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		 
		String writeValueAsString = objectMapper.writeValueAsString(obj);
		return writeValueAsString;
	}
	
}
