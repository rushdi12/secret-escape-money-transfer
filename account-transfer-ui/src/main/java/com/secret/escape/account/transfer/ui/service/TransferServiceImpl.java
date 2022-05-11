package com.secret.escape.account.transfer.ui.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.escape.account.transfer.ui.dto.TransferRequestDto;
import com.secret.escape.account.transfer.ui.dto.TransferResponseDto;

@Service
public class TransferServiceImpl implements TransferService {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${transfer.account.api.base.url}")
	private String API_ACCOUNT_TRANSFER_BASE_URL;


 
	 

	@Override
	public TransferResponseDto trransferFunds(TransferRequestDto transferRequestDto) {
		TransferResponseDto details = new TransferResponseDto();

		try {
			
			TransferRequestDto requestDto = new TransferRequestDto();
			requestDto.setFromAccountNumber(transferRequestDto.getFromAccountNumber());
			requestDto.setToAccountNumber(transferRequestDto.getToAccountNumber());
			requestDto.setAmount(transferRequestDto.getAmount());
			
			HttpEntity<TransferRequestDto> request = new HttpEntity<TransferRequestDto>(requestDto);
			
			String url = API_ACCOUNT_TRANSFER_BASE_URL;
			ResponseEntity<String> rawResponse = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
			String responseBody = rawResponse.getBody();
			if (isError(rawResponse.getStatusCode())) {
				com.secret.escape.account.transfer.ui.dto.Error error = objectMapper.readValue(responseBody, com.secret.escape.account.transfer.ui.dto.Error.class);
				throw new RestClientException("[" + error.getErrorCode()[0] + "] " + "[" + error.getErrorMessage() + "]");
			} else {
				details = objectMapper.readValue(responseBody, new TypeReference<TransferResponseDto>() {});
			}

		} catch (RestClientException ex) {
			throw ex;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		return details;

	}
	
	private boolean isError(HttpStatus status) {
		HttpStatus.Series series = status.series();
		return (HttpStatus.Series.CLIENT_ERROR.equals(series) || HttpStatus.Series.SERVER_ERROR.equals(series));
	}
	 

	
 
}
