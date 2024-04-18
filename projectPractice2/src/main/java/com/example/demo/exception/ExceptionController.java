package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler(value=UserNotFoundException.class)
	public ResponseEntity<Object> exception(UserNotFoundException userex){
		return new ResponseEntity<Object>(userex.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(value=AnswerNotFoundException.class)
	public ResponseEntity<Object> exception(AnswerNotFoundException answerex){
		return new ResponseEntity<Object>(answerex.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value=QuestionNotFoundException.class)
	public ResponseEntity<Object> exception(QuestionNotFoundException questionex){
		return new ResponseEntity<Object>(questionex.getMessage(),HttpStatus.NOT_FOUND);
	}
}
