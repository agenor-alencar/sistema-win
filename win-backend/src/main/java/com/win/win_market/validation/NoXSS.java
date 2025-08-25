package com.win.win_market.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoXSSValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoXSS {
    String message() default "Entrada contém conteúdo potencialmente perigoso";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
