package com.echoform.controller.publicapi;

import com.echoform.dto.mapper.DtoMapper;
import com.echoform.dto.response.FormResponse;
import com.echoform.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/forms/link")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PublicFormController {
    
    private final FormService formService;

    @GetMapping("/{publicLink}")
    public ResponseEntity<FormResponse> getFormByPublicLink(
            @PathVariable String publicLink,
            @CookieValue(value = "sessionId", required = false) String sessionId
    ) {
        return formService.getFormByPublicLink(publicLink)
                .map(DtoMapper::toFormResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
