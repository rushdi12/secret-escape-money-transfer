package com.secret.escape.account.transfer.ui.controller;
//package com.secret.escape.account.transfer.controller;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.client.RestClientException;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.secret.escape.account.transfer.exception.ApiError;
//import com.secret.escape.account.transfer.exception.BadRequestException;
//
//
//@ControllerAdvice
//public class RestExceptionController {
//	
//	public static final String DEFAULT_ERROR_VIEW = "error";
//	
//	
////	@ExceptionHandler(value = BadRequestException.class)
////	public ResponseEntity<Object> exception(BadRequestException exception) {
////		ApiError apiErorr = new ApiError();
////
////		apiErorr.setErrorMessage(exception.getMessage());
////
////		return new ResponseEntity<>(apiErorr, HttpStatus.BAD_REQUEST);
////	}
////
////	@ExceptionHandler(value = MethodArgumentNotValidException.class)
////	public ResponseEntity<Object> exception(MethodArgumentNotValidException exception) {
////
////		List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
////		FieldError fieldError = exception.getBindingResult().getFieldError();
////		ApiError apiErorr = new ApiError();
////
////		for (ObjectError error : allErrors) {
////
////			apiErorr.setErrorCode(error.getCodes());
////			apiErorr.setErrorMessage(error.getDefaultMessage());
////		}
////
////		return new ResponseEntity<>(apiErorr, HttpStatus.BAD_REQUEST);
////	}
////	
////	@ExceptionHandler(RestClientException.class)
////	protected ModelAndView handleRestClientException(HttpServletRequest request, RestClientException ex) {
////		 
////
////		ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);
////		mav.addObject("message", "error.message.default");
////		mav.addObject("details", ex.getMessage());
////		mav.addObject("datetime", new Date());
////		mav.addObject("exception", ex);
////		mav.addObject("url", request.getRequestURL());
////	//	mav.addObject("transactionSearchCriteria", new AdvancedTransactionSearchCriteria());
////		mav.addObject("activeView", "error");
////		//mav.addObject("country", languageLocale);
////		return mav;
////	}
//
//}
