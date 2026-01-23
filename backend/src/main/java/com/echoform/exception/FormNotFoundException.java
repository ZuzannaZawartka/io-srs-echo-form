package com.echoform.exception;

public class FormNotFoundException extends RuntimeException {
    public FormNotFoundException(Long id) {
        super("Form not found with id: " + id);
    }
    
    public FormNotFoundException(String publicLink) {
        super("Form not found with public link: " + publicLink);
    }
}
