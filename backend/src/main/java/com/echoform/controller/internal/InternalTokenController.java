package com.echoform.controller.internal;

import com.echoform.dto.mapper.DtoMapper;
import com.echoform.dto.request.TokenGenerateRequest;
import com.echoform.dto.response.TokenResponse;
import com.echoform.model.OneTimeToken;
import com.echoform.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Internal API - Token Management
 * RESTful nested resource: /api/forms/{formId}/tokens
 */
@RestController
@RequestMapping("/api/forms/{formId}/tokens")
@RequiredArgsConstructor

public class InternalTokenController {
    
    private final TokenService tokenService;
    
    /**
     * Generate token for form
     * POST /api/forms/{formId}/tokens
     */
    @PostMapping
    public ResponseEntity<TokenResponse> generateToken(
            @PathVariable Long formId,
            @Valid @RequestBody TokenGenerateRequest request
    ) {
        OneTimeToken token = tokenService.generateToken(
                formId,
                request.expiresInMinutes()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.toTokenResponse(token));
    }
    
    /**
     * List all tokens for form
     * GET /api/forms/{formId}/tokens
     */
    @GetMapping
    public ResponseEntity<List<TokenResponse>> getTokens(@PathVariable Long formId) {
        List<TokenResponse> tokens = tokenService.getTokensByFormId(formId).stream()
                .map(DtoMapper::toTokenResponse)
                .toList();
        return ResponseEntity.ok(tokens);
    }
}
