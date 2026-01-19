package com.echoform.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TokenValidateRequest(
    @NotBlank(message = "Public link is required")
    String publicLink,
    
    @NotBlank(message = "Token value is required")
    String tokenValue
) {}
