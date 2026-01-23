package com.echoform.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxExpiryValidator.class)
public @interface MaxExpiry {
    String message() default "Expiry time cannot exceed {maxDays} days";
    int maxDays() default 7;
    
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
