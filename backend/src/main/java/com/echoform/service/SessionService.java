package com.echoform.service;

import com.echoform.model.Session;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service for managing respondent sessions
 * 
 * Sessions are stored in-memory and last 10 minutes.
 * Each session grants access to exactly ONE form.
 */
@Service
public class SessionService {
    
    private static final int SESSION_DURATION_MINUTES = 10;
    private final Map<String, Session> sessionStore = new ConcurrentHashMap<>();
    
    /**
     * Create a new session for form access
     * 
     * @param formId ID of the form to grant access to
     * @return Session ID (UUID)
     */
    public String createSession(Long formId) {
        String sessionId = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(SESSION_DURATION_MINUTES);
        
        Session session = new Session(formId, expiresAt);
        sessionStore.put(sessionId, session);
        
        return sessionId;
    }
    
    /**
     * Validate session and check if it grants access to specific form
     * 
     * @param sessionId Session ID from cookie
     * @param formId Form ID to check access for
     * @return true if session is valid and grants access to this form
     */
    public boolean isValidForForm(String sessionId, Long formId) {
        if (sessionId == null || formId == null) {
            return false;
        }
        
        Session session = sessionStore.get(sessionId);
        
        if (session == null) {
            return false; // Session doesn't exist
        }
        
        if (!session.isValid()) {
            sessionStore.remove(sessionId); // Clean up expired session
            return false;
        }
        
        return session.getFormId().equals(formId);
    }
    
    /**
     * Get session expiry time
     * 
     * @param sessionId Session ID
     * @return Expiry time or null if session doesn't exist
     */
    public LocalDateTime getExpiresAt(String sessionId) {
        Session session = sessionStore.get(sessionId);
        return session != null ? session.getExpiresAt() : null;
    }
    
    /**
     * Clean up expired sessions (optional maintenance task)
     * Call this periodically to prevent memory leaks
     */
    public void cleanupExpiredSessions() {
        sessionStore.entrySet().removeIf(entry -> !entry.getValue().isValid());
    }
}
