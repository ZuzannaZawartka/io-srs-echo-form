package com.echoform.controller.publicapi;

import com.echoform.dto.mapper.DtoMapper;
import com.echoform.dto.response.FormMetadataResponse;
import com.echoform.dto.response.FormResponse;
import com.echoform.model.Form;
import com.echoform.service.FormService;
import com.echoform.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Public API Controller for Form Access
 */
@RestController
@RequestMapping("/api/public/forms")
@RequiredArgsConstructor

public class PublicFormController {
    
    private final FormService formService;
    private final SessionService sessionService;
    
    /**
     * Get form by public link
     */
    @GetMapping("/{publicLink}")
    public ResponseEntity<?> getFormByPublicLink(
            @PathVariable String publicLink,
            @CookieValue(value = "sessionId", required = false) String sessionId
    ) {
        // Find form
        Form form = formService.getFormByPublicLink(publicLink)
                .orElse(null);
        
        if (form == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Check if user has valid session for THIS form
        if (sessionId != null && sessionService.isValidForForm(sessionId, form.getId())) {
            // Valid session, return FULL form with content
            return ResponseEntity.ok(DtoMapper.toFormResponse(form));
        }
        
        // No valid session, return ONLY metadata (no content)
        return ResponseEntity.ok(DtoMapper.toFormMetadataResponse(form));
    }
}
