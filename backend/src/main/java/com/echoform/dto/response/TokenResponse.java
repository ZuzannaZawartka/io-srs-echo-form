package com.echoform.dto.response;

import java.time.LocalDateTime;

public record TokenResponse(
    String tokenValue,
    Long formId,
    LocalDateTime expiresAt,
    Boolean isUsed,
    LocalDateTime createdAt
) {}
