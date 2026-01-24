package com.echoform.controller.publicapi;

import com.echoform.dto.mapper.DtoMapper;
import com.echoform.dto.request.TokenValidateRequest;
import com.echoform.dto.response.FormResponse;
import com.echoform.dto.response.TokenValidateResponse;
import com.echoform.model.OneTimeToken;
import com.echoform.service.SessionService;
import com.echoform.service.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Public API Controller for Token Validation
*/
@RestController
@RequestMapping("/api/public/tokens")
@RequiredArgsConstructor

public class PublicTokenController {
    
    private final TokenService tokenService;
    private final SessionService sessionService;
    
    /**
     * Validate token and grant access to form
     * POST /api/public/tokens
     */
    @PostMapping
    public ResponseEntity<TokenValidateResponse> validateToken(
            @Valid @RequestBody TokenValidateRequest request,
            HttpServletResponse response
    ) {
        // Validate token and mark as used
        OneTimeToken token = tokenService.validateAndUseToken(
                request.tokenValue(),
                request.publicLink()
        );
        
        // Create session (10 minutes)
        String sessionId = sessionService.createSession(token.getForm().getId());
        LocalDateTime sessionExpiresAt = sessionService.getExpiresAt(sessionId);
        
        // Set HTTP-only cookie
        Cookie sessionCookie = new Cookie("sessionId", sessionId);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setPath("/");
        sessionCookie.setMaxAge(600); // 10 minutes in seconds
        sessionCookie.setSecure(false); // Set to true in production with HTTPS
        // sessionCookie.setSameSite("Strict")
        response.addCookie(sessionCookie);
        
        // Also set SameSite via response header
        response.addHeader("Set-Cookie", 
            "sessionId=" + sessionId + "; HttpOnly; Path=/; Max-Age=600; SameSite=Strict");
        
        // Return form data + session info
        FormResponse formResponse = DtoMapper.toFormResponse(token.getForm());
        TokenValidateResponse validateResponse = new TokenValidateResponse(
                formResponse,
                true,
                sessionId,
                sessionExpiresAt
        );
        
        return ResponseEntity.ok(validateResponse);
    }
}
