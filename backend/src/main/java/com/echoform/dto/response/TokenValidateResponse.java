package com.echoform.dto.response;

import java.time.LocalDateTime;

/**
 * Response DTO for token validation
 * 
 * Returned after successful token validation.
 * Contains the full form data and session information.
 */
public record TokenValidateResponse(
    FormResponse form,
    Boolean tokenValid,
    String sessionId,
    LocalDateTime sessionExpiresAt
) {}
