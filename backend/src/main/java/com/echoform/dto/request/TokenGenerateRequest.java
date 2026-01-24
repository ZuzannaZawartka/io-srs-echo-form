package com.echoform.dto.request;

import com.echoform.config.AppConstants;
import com.echoform.validation.MaxExpiry;
import jakarta.validation.constraints.Min;

public record TokenGenerateRequest(
    @Min(value = AppConstants.MIN_TOKEN_EXPIRY_MINUTES, message = "Expiry time must be at least 1 minute")
    @MaxExpiry(maxDays = AppConstants.MAX_TOKEN_EXPIRY_DAYS, message = "Expiry time cannot exceed 7 days")
    Integer expiresInMinutes
) {
    public TokenGenerateRequest {
        if (expiresInMinutes == null) {
            expiresInMinutes = AppConstants.DEFAULT_TOKEN_EXPIRY_MINUTES;
        }
    }
}
