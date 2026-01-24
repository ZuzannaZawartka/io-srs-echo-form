package com.echoform.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Session model for respondent access
 * 
 * Stores temporary session data:
 * - formId: which form this session grants access to
 * - expiresAt: when session expires (10 minutes from creation)
 * 
 * Sessions are stored in-memory (not persisted to database)
 */
@Data
@AllArgsConstructor
public class Session {
    
    private Long formId;
    private LocalDateTime expiresAt;

    public boolean isValid() {
        return LocalDateTime.now().isBefore(expiresAt);
    }
}
