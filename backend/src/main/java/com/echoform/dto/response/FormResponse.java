package com.echoform.dto.response;

import java.time.LocalDateTime;

public record FormResponse(
    Long id,
    String title,
    String content,
    String publicLink,
    LocalDateTime createdAt
) {}
