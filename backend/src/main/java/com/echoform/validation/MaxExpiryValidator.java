package com.echoform.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MaxExpiryValidator implements ConstraintValidator<MaxExpiry, Integer> {
    
    private int maxMinutes;
    
    @Override
    public void initialize(MaxExpiry annotation) {
        this.maxMinutes = annotation.maxDays() * 24 * 60;
    }
    
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value <= maxMinutes;
    }
}
