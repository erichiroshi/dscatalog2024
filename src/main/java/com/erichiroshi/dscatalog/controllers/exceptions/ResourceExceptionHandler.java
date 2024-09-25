package com.erichiroshi.dscatalog.controllers.exceptions;

import java.time.LocalDateTime;

import org.bouncycastle.openssl.PasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.erichiroshi.dscatalog.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        String error = "Resource not found.";
        int status = HttpStatus.NOT_FOUND.value();

        StandardError err = new StandardError(LocalDateTime.now(), status, error, ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
    
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(UsernameNotFoundException ex, HttpServletRequest request) {
    	String error = "User not found.";
    	int status = HttpStatus.BAD_REQUEST.value();
    	
    	StandardError err = new StandardError(LocalDateTime.now(), status, error, ex.getMessage(), request.getRequestURI());
    	return ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<StandardError> entityNotFound(PasswordException ex, HttpServletRequest request) {
    	String error = "Password Invalid.";
    	int status = HttpStatus.BAD_REQUEST.value();
    	
    	StandardError err = new StandardError(LocalDateTime.now(), status, error, ex.getMessage(), request.getRequestURI());
    	return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> methodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String error = "Validation exception";
        int status = HttpStatus.UNPROCESSABLE_ENTITY.value();

        ValidationError err = new ValidationError(LocalDateTime.now(), status, error, ex.getMessage(), request.getRequestURI());

        for (FieldError f : ex.getBindingResult().getFieldErrors()) {
            err.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }
}