package com.echoform.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FormCreateRequest(
    @NotBlank(message = "Title is required")
    String title,
    String content
) {}
