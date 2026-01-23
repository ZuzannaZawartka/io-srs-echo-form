package com.echoform.exception;

public class TokenAlreadyUsedException extends RuntimeException {
    public TokenAlreadyUsedException(String tokenValue) {
        super("Token already used: " + tokenValue);
    }
}
