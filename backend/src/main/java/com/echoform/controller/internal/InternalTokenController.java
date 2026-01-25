package com.echoform.controller.internal;

import com.echoform.dto.request.TokenGenerateRequest;
import com.echoform.dto.response.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.authentication.ott.OneTimeTokenService;
import org.springframework.security.authentication.ott.GenerateOneTimeTokenRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/forms/{formId}/tokens")
@RequiredArgsConstructor

public class InternalTokenController {
    
    private final OneTimeTokenService oneTimeTokenService;
    private final com.echoform.repository.OneTimeTokenRepository oneTimeTokenRepository;
    private final com.echoform.service.FormService formService;
    
    @PostMapping
    public ResponseEntity<TokenResponse> generateToken(
            @PathVariable Long formId,
            @Valid @RequestBody TokenGenerateRequest request
    ) {
        formService.getFormByIdOrThrow(formId);

        String formPrincipal = "form:" + formId;
        
        var generateRequest = new GenerateOneTimeTokenRequest(
            formPrincipal,
            java.time.Duration.ofMinutes(request.expiresInMinutes())
        );

        org.springframework.security.authentication.ott.OneTimeToken token = oneTimeTokenService.generate(generateRequest);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new TokenResponse(
                token.getTokenValue(),
                formId,
                token.getExpiresAt().atZone(ZoneId.of("UTC")).toLocalDateTime(),
                LocalDateTime.now()
            )
        );
    }

    @GetMapping
    public ResponseEntity<List<TokenResponse>> getTokens(@PathVariable Long formId) {
        formService.getFormByIdOrThrow(formId);
        List<TokenResponse> tokens = oneTimeTokenRepository.findTokensByFormId(formId);
        return ResponseEntity.ok(tokens);
    }
}
