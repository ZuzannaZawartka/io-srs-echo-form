package com.echoform.controller.publicapi;

import com.echoform.dto.mapper.DtoMapper;
import com.echoform.dto.response.FormResponse;
import com.echoform.model.Form;
import com.echoform.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Public API Controller for Form Access
 */
@RestController
@RequestMapping("/api/public/forms")
@RequiredArgsConstructor

    public class PublicFormController {
    
    private final FormService formService;
    
    /**
     * Get form metadata
     */
    @GetMapping("/{publicLink}/meta")
    public ResponseEntity<FormResponse> getPublicFormMeta(@PathVariable String publicLink) {
        Form form = formService.getFormByPublicLinkOrThrow(publicLink);
        return ResponseEntity.ok(DtoMapper.toFormMetaResponse(form));
    }

    /**
     * Get full form content
     */
    @GetMapping("/{publicLink}")
    public ResponseEntity<FormResponse> getPublicFormContent(
            @PathVariable String publicLink,
            Authentication authentication
    ) {
        Form form = formService.getFormByPublicLinkOrThrow(publicLink);

        validateAccess(authentication, form.getId());
        
        return ResponseEntity.ok(DtoMapper.toFormResponse(form));
    }

    private void validateAccess(Authentication authentication, Long requestedFormId) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new org.springframework.security.access.AccessDeniedException("User not authenticated");
        }
        
        String principle = authentication.getName();
        String expectedPrincipal = "form:" + requestedFormId;
        
        if (!expectedPrincipal.equals(principle)) {
            throw new org.springframework.security.access.AccessDeniedException("Access denied: Token mismatch for form " + requestedFormId);
        }
    }
}
