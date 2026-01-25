package com.echoform.dto.mapper;


import com.echoform.dto.response.FormResponse;
import com.echoform.model.Form;

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

    public static FormResponse toFormMetaResponse(Form form) {
        return new FormResponse(
            form.getId(),
            form.getTitle(),
            null, // Hide content for public meta access
            form.getPublicLink(),
            form.getCreatedAt()
        );
    }

}
