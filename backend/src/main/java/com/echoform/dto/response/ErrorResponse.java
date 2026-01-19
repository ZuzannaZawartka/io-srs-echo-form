package com.echoform.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
    String message,
    List<String> errors,
    LocalDateTime timestamp,
    String path
) {
    public ErrorResponse(String message, List<String> errors, String path) {
        this(message, errors, LocalDateTime.now(), path);
    }
    
    public ErrorResponse(String message, String path) {
        this(message, List.of(), LocalDateTime.now(), path);
    }
}
