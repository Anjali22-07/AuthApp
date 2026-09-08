package com.lcp.auth.auth.dtos;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, HttpStatus httpstatus) {

}
