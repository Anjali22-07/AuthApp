package com.lcp.auth.auth.Exceptions;

import java.net.http.HttpRequest;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import javax.naming.AuthenticationException;
import javax.security.auth.login.CredentialExpiredException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lcp.auth.auth.dtos.ApiError;
import com.lcp.auth.auth.dtos.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException exception){
        ErrorResponse error= new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalException(IllegalArgumentException exception){
        ErrorResponse error= new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        
    }
//Handles exceptions coming from servlet 
    @ExceptionHandler({
        UsernameNotFoundException.class,
        BadCredentialsException.class,
        CredentialExpiredException.class,
        DisabledException.class,
    })
    public ResponseEntity<ApiError> handleAuthException(Exception e, HttpServletRequest request){
       logger.info("Exception: "+ e.getClass().getName());
        var apiError= ApiError.of(HttpStatus.BAD_REQUEST.value(),"BAD REQUEST", e.getMessage(), request.getRequestURI(),OffsetDateTime.now(ZoneOffset.UTC));
            return ResponseEntity.badRequest().body(apiError);

}
}
