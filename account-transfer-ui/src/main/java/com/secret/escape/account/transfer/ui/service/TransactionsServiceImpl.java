package com.secret.escape.account.transfer.ui.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.secret.escape.account.transfer.ui.dto.TransactionsDto;
import com.secret.escape.account.transfer.ui.dto.TransactionsRequestDto;

@Service
public class TransactionsServiceImpl implements TransactionService {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${transactions.transfer.api.base.url}")
	private String API_TRANSACTIONS_BASE_URL;

	 
	@Override
	public List<TransactionsDto> findTransactionsForAccountId(Long id) {
		List<TransactionsDto> details = new ArrayList<TransactionsDto>();

		try {
			
			String url = API_TRANSACTIONS_BASE_URL + "/" + id;
			ResponseEntity<String> rawResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
			String responseBody = rawResponse.getBody();
			if (isError(rawResponse.getStatusCode())) {
				com.secret.escape.account.transfer.ui.dto.Error error = objectMapper.readValue(responseBody, com.secret.escape.account.transfer.ui.dto.Error.class);
				throw new RestClientException("[" + error.getErrorCode()[0] + "] " + "[" + error.getErrorMessage() + "]");
			} else {
				details = objectMapper.readValue(responseBody, new TypeReference<List<TransactionsDto>>() {});
			}

		} catch (RestClientException ex) {
			throw ex;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		return details;

	}


	@Override
	public List<TransactionsDto> findTransactionsForAccountNumber(Long accountNumber) {
		List<TransactionsDto> details = new ArrayList<TransactionsDto>();

		try {
			
			TransactionsRequestDto requestDto = new TransactionsRequestDto();
			requestDto.setAccountNumber(String.valueOf(accountNumber));
			HttpEntity<TransactionsRequestDto> request = new HttpEntity<TransactionsRequestDto>(requestDto);
			
			String url = API_TRANSACTIONS_BASE_URL;
			ResponseEntity<String> rawResponse = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
			String responseBody = rawResponse.getBody();
			if (isError(rawResponse.getStatusCode())) {
				com.secret.escape.account.transfer.ui.dto.Error error = objectMapper.readValue(responseBody, com.secret.escape.account.transfer.ui.dto.Error.class);
				throw new RestClientException("[" + error.getErrorCode()[0] + "] " + "[" + error.getErrorMessage() + "]");
			} else {
				details = objectMapper.readValue(responseBody, new TypeReference<List<TransactionsDto>>() {});
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
