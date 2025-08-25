package com.win.win_market.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "A senha deve ter pelo menos 8 caracteres, incluindo maiúscula, minúscula, número e caractere especial";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
