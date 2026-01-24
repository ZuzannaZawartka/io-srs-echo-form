package com.echoform.task;

import com.echoform.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled task for cleaning up expired sessions
 * 
 * Runs every 5 minutes to remove expired sessions from memory.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SessionCleanupTask {
    
    private final SessionService sessionService;
    
    /**
     * Clean up expired sessions every 5 minutes
     */
    @Scheduled(fixedDelay = 300000) // 5 minutes = 300,000 milliseconds
    public void cleanupExpiredSessions() {
        log.debug("Running scheduled session cleanup task");
        sessionService.cleanupExpiredSessions();
        log.debug("Session cleanup completed");
    }
}
