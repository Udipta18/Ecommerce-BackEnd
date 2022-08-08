package com.back.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.back.payload.ApiResponse;



@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
		ApiResponse api=new ApiResponse(ex.getMessage(), false);
		return new ResponseEntity<ApiResponse>(api,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadUserLoginDetailsException.class)
	public ResponseEntity<ApiResponse> handleBadUserLoginDetailsException(BadUserLoginDetailsException ex) {
		ApiResponse api=new ApiResponse(ex.getMessage(), false);
		return new ResponseEntity<ApiResponse>(api,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		   Map<String,String> map=new HashMap<>();
		   ex.getBindingResult().getAllErrors().forEach((error) ->{
		          String message=error.getDefaultMessage();
		          String fieldName=((FieldError) error).getField();
		          map.put(fieldName, message);
		   });
		return new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
	}
}
