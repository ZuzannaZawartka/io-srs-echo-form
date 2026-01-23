package com.echoform.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String tokenValue) {
        super("Token expired: " + tokenValue);
    }
}
