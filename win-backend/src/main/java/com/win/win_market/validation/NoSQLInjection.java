package com.win.win_market.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoSQLInjectionValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoSQLInjection {
    String message() default "Entrada contém possível tentativa de SQL Injection";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
