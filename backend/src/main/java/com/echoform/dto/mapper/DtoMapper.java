package com.echoform.dto.mapper;

import com.echoform.dto.response.FormMetadataResponse;
import com.echoform.dto.response.FormResponse;
import com.echoform.dto.response.TokenResponse;
import com.echoform.model.Form;
import com.echoform.model.OneTimeToken;

public class DtoMapper {
    
    public static FormResponse toFormResponse(Form form) {
        return new FormResponse(
            form.getId(),
            form.getTitle(),
            form.getContent(),
            form.getPublicLink(),
            form.getCreatedAt()
        );
    }
    
    public static FormMetadataResponse toFormMetadataResponse(Form form) {
        return new FormMetadataResponse(
            form.getId(),
            form.getTitle(),
            form.getPublicLink(),
            true,
            form.getCreatedAt()
        );
    }
    
    public static TokenResponse toTokenResponse(OneTimeToken token) {
        return new TokenResponse(
            token.getTokenValue(),
            token.getForm().getId(),
            token.getExpiresAt(),
            token.getIsUsed(),
            token.getCreatedAt()
        );
    }
}
