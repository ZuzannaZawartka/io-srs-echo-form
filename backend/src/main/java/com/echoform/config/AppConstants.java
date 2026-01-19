package com.echoform.config;

public final class AppConstants {
    
    private AppConstants() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    public static final int DEFAULT_TOKEN_EXPIRY_MINUTES = 60;
    public static final int MIN_TOKEN_EXPIRY_MINUTES = 1;
    public static final int MAX_TOKEN_EXPIRY_DAYS = 7;
    public static final int MAX_TOKEN_EXPIRY_MINUTES = MAX_TOKEN_EXPIRY_DAYS * 24 * 60;
    
    public static final int SESSION_DURATION_MINUTES = 10;
    
    public static final int PUBLIC_LINK_LENGTH = 32;
    public static final int TOKEN_VALUE_LENGTH = 24;
    public static final String TOKEN_PREFIX = "OTT-";
}
