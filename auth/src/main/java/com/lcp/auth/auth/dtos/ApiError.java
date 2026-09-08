package com.lcp.auth.auth.dtos;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record ApiError(
    int status,
    String message,
    String error,
    String path,
    OffsetDateTime timeStamp
) {
  
     public static ApiError of(int status, String message, String error, String path, OffsetDateTime timeStamp){
        return new ApiError(status, message, error, path, OffsetDateTime.now(ZoneOffset.UTC));
     }
}
