package com.bcnit14.controller;

import java.security.AccessControlException;
import java.util.NoSuchElementException;

import javax.servlet.ServletException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ErrorController extends DefaultResponseErrorHandler {

	@ExceptionHandler(AccessControlException.class)
	@ResponseBody
	public ResponseEntity<StringDto> requestForbidden() {
		return new ResponseEntity<>(StringDto.stringToJson("You shall not pass!"), HttpStatus.FORBIDDEN);

	}

	@ExceptionHandler({ ServletException.class, NoSuchElementException.class })
	@ResponseBody
	public ResponseEntity<StringDto> requestNotFound() {
		return new ResponseEntity<>(StringDto.stringToJson("That id is not in our database."), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class })
	@ResponseBody
	public ResponseEntity<StringDto> invalidRequest() {
		return new ResponseEntity<>(
				StringDto.stringToJson("Please check if the id is a number or the other paramethers are ok."),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class, NullPointerException.class })
	@ResponseBody
	public ResponseEntity<StringDto> cantFound() {
		return new ResponseEntity<>(StringDto.stringToJson("Sorry,we can't find that player"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
