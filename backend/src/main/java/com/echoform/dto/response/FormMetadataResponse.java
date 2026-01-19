package com.echoform.dto.response;

import java.time.LocalDateTime;

public record FormMetadataResponse(
    Long id,
    String title,
    String publicLink,
    Boolean requiresToken,
    LocalDateTime createdAt
) {}
