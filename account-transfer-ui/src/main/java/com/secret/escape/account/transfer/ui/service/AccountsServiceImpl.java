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
import com.secret.escape.account.transfer.ui.dto.AccountDto;
import com.secret.escape.account.transfer.ui.dto.UpdateEmailRequestDto;

@Service
public class AccountsServiceImpl implements AccountsService {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${account.transfer.api.base.url}")
	private String API_ACCOUNTS_BASE_URL;

	@Override
	public List<AccountDto> findAllAccounts() {

		List<AccountDto> details = new ArrayList<AccountDto>();

		try {
			String url = API_ACCOUNTS_BASE_URL + "all";
			ResponseEntity<String> rawResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
			String responseBody = rawResponse.getBody();
			if (isError(rawResponse.getStatusCode())) {
				com.secret.escape.account.transfer.ui.dto.Error error = objectMapper.readValue(responseBody, com.secret.escape.account.transfer.ui.dto.Error.class);
				throw new RestClientException("[" + error.getErrorCode()[0] + "] " + "[" + error.getErrorMessage() + "]");
			} else {
				details = objectMapper.readValue(responseBody, new TypeReference<List<AccountDto>>() {});
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


	@Override
	public AccountDto findAccountByAccountNumber(Long accountNumber) {
		AccountDto details = new AccountDto();

		try {

			String url = API_ACCOUNTS_BASE_URL + "account-number/" + accountNumber ;
			ResponseEntity<String> rawResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
			String responseBody = rawResponse.getBody();
			if (isError(rawResponse.getStatusCode())) {
				com.secret.escape.account.transfer.ui.dto.Error error = objectMapper.readValue(responseBody, com.secret.escape.account.transfer.ui.dto.Error.class);
				throw new RestClientException("[" + error.getErrorCode()[0] + "] " + "[" + error.getErrorMessage() + "]");
			} else {
				details = objectMapper.readValue(responseBody, new TypeReference<AccountDto>() {});
			}

		} catch (RestClientException ex) {
			throw ex;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		return details;

	}

	@Override
	public AccountDto updateAccountEmail(String accountNumber, String email) {
		
		AccountDto details = new AccountDto();

		try {
			
			UpdateEmailRequestDto requestDto = new UpdateEmailRequestDto();
			requestDto.setAccountNumber(String.valueOf(accountNumber));
			requestDto.setEmail(email);
			
			HttpEntity<UpdateEmailRequestDto> request = new HttpEntity<UpdateEmailRequestDto>(requestDto);
			
			String url = API_ACCOUNTS_BASE_URL + "update/email";
			ResponseEntity<String> rawResponse = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
			String responseBody = rawResponse.getBody();
			if (isError(rawResponse.getStatusCode())) {
				com.secret.escape.account.transfer.ui.dto.Error error = objectMapper.readValue(responseBody, com.secret.escape.account.transfer.ui.dto.Error.class);
				throw new RestClientException("[" + error.getErrorCode()[0] + "] " + "[" + error.getErrorMessage() + "]");
			} else {
				details = objectMapper.readValue(responseBody, new TypeReference<AccountDto>() {});
			}

		} catch (RestClientException ex) {
			throw ex;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		return details;

	}

 
}
