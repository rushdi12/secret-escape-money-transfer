package com.secret.escape.account.transfer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.secret.escape.account.transfer.exception.ApiError;
import com.secret.escape.account.transfer.exception.BadRequestException;

@ControllerAdvice
public class RestExceptionController {
	@ExceptionHandler(value = BadRequestException.class)
	public ResponseEntity<Object> exception(BadRequestException exception) {
		ApiError apiErorr = new ApiError();

		apiErorr.setErrorMessage(exception.getMessage());

		return new ResponseEntity<>(apiErorr, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Object> exception(MethodArgumentNotValidException exception) {

		List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
		ApiError apiErorr = new ApiError();

		for (ObjectError error : allErrors) {

			apiErorr.setErrorCode(error.getCodes());
			apiErorr.setErrorMessage(error.getDefaultMessage());
		}

		return new ResponseEntity<>(apiErorr, HttpStatus.BAD_REQUEST);
	}

}
