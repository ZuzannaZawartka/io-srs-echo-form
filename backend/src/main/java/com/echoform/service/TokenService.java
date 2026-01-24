package com.echoform.service;

import com.echoform.exception.FormNotFoundException;
import com.echoform.model.Form;
import com.echoform.model.OneTimeToken;
import com.echoform.repository.FormRepository;
import com.echoform.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

/**
 * Service for managing one-time access tokens
 * 
 * Handles:
 * - Token generation with custom expiry time
 * - Token retrieval by form ID
 * - Token format: "OTT-{20 random characters}"
 */
@Service
@RequiredArgsConstructor
public class TokenService {
    
    private final TokenRepository tokenRepository;
    private final FormRepository formRepository;
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final String TOKEN_PREFIX = "OTT-";
    private static final int TOKEN_LENGTH = 20;
    
    /**
     * Generate a new one-time token for a form
     * 
     * @param formId ID of the form
     * @param expiresInMinutes Time in minutes until token expires (default: 60)
     * @return Generated token
     * @throws FormNotFoundException if form doesn't exist
     */
    @Transactional
    public OneTimeToken generateToken(Long formId, Integer expiresInMinutes) {
        // Validate form exists
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new FormNotFoundException(formId));
        
        // Generate unique token value
        String tokenValue = generateUniqueTokenValue();
        
        // Calculate expiry time
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(expiresInMinutes);
        
        // Create token entity
        OneTimeToken token = new OneTimeToken();
        token.setForm(form);
        token.setTokenValue(tokenValue);
        token.setExpiresAt(expiresAt);
        token.setIsUsed(false);
        
        return tokenRepository.save(token);
    }
    
    /**
     * Get all tokens for a specific form
     * 
     * @param formId ID of the form
     * @return List of tokens (may be empty)
     */
    public List<OneTimeToken> getTokensByFormId(Long formId) {
        return tokenRepository.findByFormId(formId);
    }
    
    /**
     * Validate token and mark as used (one-time use)
     * @param tokenValue Token value to validate
     * @param publicLink Public link of the form
     * @return The OneTimeToken if valid 
     * @throws com.echoform.exception.TokenNotFoundException if token doesn't exist
     * @throws com.echoform.exception.TokenExpiredException if token expired
     * @throws com.echoform.exception.TokenAlreadyUsedException if token already used
     * @throws com.echoform.exception.FormNotFoundException if form doesn't match
     */
    @Transactional
    public OneTimeToken validateAndUseToken(String tokenValue, String publicLink) {
        OneTimeToken token = tokenRepository.findByTokenValue(tokenValue)
                .orElseThrow(() -> new com.echoform.exception.TokenNotFoundException(tokenValue));
        
        if (LocalDateTime.now().isAfter(token.getExpiresAt())) {
            throw new com.echoform.exception.TokenExpiredException(tokenValue);
        }
        
        if (token.getIsUsed()) {
            throw new com.echoform.exception.TokenAlreadyUsedException(tokenValue);
        }
        
        Form tokenForm = token.getForm();
        if (!publicLink.equals(tokenForm.getPublicLink())) {
            throw new com.echoform.exception.FormNotFoundException(
                "Token doesn't match this form"
            );
        }
        
        token.setIsUsed(true);
        token.setUsedAt(LocalDateTime.now());
        tokenRepository.save(token);
        
        return token;
    }
    
    /**
     * Generate a unique token value in format: "OTT-{20 random chars}"
     * Uses SecureRandom and Base64 URL-safe encoding
     * 
     * @return Unique token value
     */
    private String generateUniqueTokenValue() {
        byte[] randomBytes = new byte[15]; // 15 bytes = 20 Base64 chars
        secureRandom.nextBytes(randomBytes);
        
        String randomPart = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes)
                .substring(0, TOKEN_LENGTH);
        
        return TOKEN_PREFIX + randomPart;
    }
}
