package com.secret.escape.account.transfer.controller;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.mail.Address;
import javax.mail.internet.MimeMessage;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.secret.escape.account.transfer.dto.TransferRequestDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("async")
public class TransferControllerIntergrationTest extends AbstractTestController {

	@RegisterExtension
	static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
	  .withConfiguration(GreenMailConfiguration.aConfig().withUser("rushdi", "secrectescape"))
	  .withPerMethodLifecycle(false);
	
	@Test
	public void givenATransferRequest_whenProcessingATransfer_thenReturnSuccess() throws Exception {
	 	
		TransferRequestDto request = new TransferRequestDto();
		request.setAmount(60L);
		request.setFromAccountNumber(account_one_accountNumber);
		request.setToAccountNumber(account_two_accountNumber);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transfer")
				.content(mapToJson(request))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void givenATransferRequest_whenProcessingATransfer_thenReturnSuccessAndCheckEmailSent() throws Exception {
	 	
		TransferRequestDto request = new TransferRequestDto();
		request.setAmount(60L);
		request.setFromAccountNumber(account_one_accountNumber);
		request.setToAccountNumber(account_two_accountNumber);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transfer")
				.content(mapToJson(request))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
 
		//Pause to all for email delivery
		await().atMost(2, SECONDS).untilAsserted(() -> {
		  MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
		  //check that 2 email's where delivered
		  assertEquals(2, receivedMessages.length);
		
		  //Validate first email
 		  MimeMessage receivedMessageOne = greenMail.getReceivedMessages()[0];
		  assertEquals(1, receivedMessageOne.getAllRecipients().length);
		  //we cant be sure what email we validating first so checking either email addresses
		  Assert.assertTrue(isExpectedEmailInRecpient(receivedMessageOne.getAllRecipients(), account_one_email, account_two_email));
		  Assert.assertTrue(GreenMailUtil.getBody(receivedMessageOne).contains("Payment Notification"));
		  
		  //Validate second email
		  MimeMessage receivedMessageTwo = greenMail.getReceivedMessages()[0];
		  assertEquals(1, receivedMessageTwo.getAllRecipients().length);
		  //we cant be sure what email we validating first so checking either email addresses
		  Assert.assertTrue(isExpectedEmailInRecpient(receivedMessageTwo.getAllRecipients(), account_one_email, account_two_email));
		  Assert.assertTrue(GreenMailUtil.getBody(receivedMessageTwo).contains("Payment Notification"));
		   
		});
		
	}
	
	@Test
	public void givenATransferRequest_whenInsufficientBalance_thenReturnBadRequestAndCheckNoEmailSent() throws Exception {

		TransferRequestDto request = new TransferRequestDto();
		request.setAmount(20001L);
		request.setFromAccountNumber(account_one_accountNumber);
		request.setToAccountNumber(account_two_accountNumber);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transfer").content(mapToJson(request))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

		// Pause to all for email delivery
		await().atMost(2, SECONDS).untilAsserted(() -> {
			MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
			
			assertTrue(receivedMessages.length == 0);

		});
	}

	@Test
	public void givenATransferRequest_whenInsufficientBalance_thenReturnBadRequest() throws Exception {
	 	
		TransferRequestDto request = new TransferRequestDto();
		request.setAmount(20001L);
		request.setFromAccountNumber(account_one_accountNumber);
		request.setToAccountNumber(account_two_accountNumber);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transfer")
				.content(mapToJson(request))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		

	}
	
	@Test
	public void givenATransferRequest_whenInvalidAccountNumber_thenReturnBadRequest() throws Exception {
	 	
		TransferRequestDto request = new TransferRequestDto();
		request.setAmount(20L);
		request.setFromAccountNumber("XXXXXXXXX");
		request.setToAccountNumber(account_two_accountNumber);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transfer")
				.content(mapToJson(request))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		

	}
	
	@Test
	public void givenATransferRequest_whenFundsAvailableAreEqualToRequest_thenReturnSuccess() throws Exception {
	 	
		TransferRequestDto request = new TransferRequestDto();
		request.setAmount(20000L);
		request.setFromAccountNumber(account_one_accountNumber);
		request.setToAccountNumber(account_two_accountNumber);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transfer")
				.content(mapToJson(request))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		

	}
	
	@Test
	public void givenATransferRequest_whenFromAndToAccountAreEqual_thenReturnBadRequest() throws Exception {
	 	
		TransferRequestDto request = new TransferRequestDto();
		request.setAmount(10000L);
		request.setFromAccountNumber(account_one_accountNumber);
		request.setToAccountNumber(account_one_accountNumber);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transfer")
				.content(mapToJson(request))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		

	}
	
	@Test
	public void givenATransferRequest_whenNullAmount_thenReturnBadRequest() throws Exception {
	 	
		TransferRequestDto request = new TransferRequestDto();
		request.setAmount(null);
		request.setFromAccountNumber(account_one_accountNumber);
		request.setToAccountNumber(account_two_accountNumber);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transfer")
				.content(mapToJson(request))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		

	}
	 
	@Test
	public void givenATransferRequest_whenZeroTransferAmountisGiven_thenReturnBadRequest() throws Exception {
	 	
		TransferRequestDto request = new TransferRequestDto();
		request.setAmount(0L);
		request.setFromAccountNumber(account_one_accountNumber);
		request.setToAccountNumber(account_two_accountNumber);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transfer")
				.content(mapToJson(request))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		

	}

	boolean isExpectedEmailInRecpient(Address[] allRecipients, String ...emails ) {
		boolean isEmailInRecipient = false;
		for(String email : emails) {
			
			for(Address address : allRecipients) {
				if(email.equalsIgnoreCase(address.toString())){
					isEmailInRecipient = true;
				}
			}
		}
		
		return isEmailInRecipient;
	}
}
