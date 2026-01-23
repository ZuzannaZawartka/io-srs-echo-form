package com.echoform.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "echoform")
public class EchoFormProperties {
    
    private TokenConfig token = new TokenConfig();
    private SessionConfig session = new SessionConfig();
    
    @Data
    public static class TokenConfig {
        private int defaultExpiryMinutes = 60;
        private int minExpiryMinutes = 1;
        private int maxExpiryDays = 7;
    }
    
    @Data
    public static class SessionConfig {
        private int durationMinutes = 10;
    }
}
