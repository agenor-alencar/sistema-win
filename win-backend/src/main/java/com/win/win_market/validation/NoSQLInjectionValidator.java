package com.win.win_market.validation;

import com.win.win_market.util.SecurityValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NoSQLInjectionValidator implements ConstraintValidator<NoSQLInjection, String> {

    @Autowired
    private SecurityValidator securityValidator;

    @Override
    public void initialize(NoSQLInjection constraintAnnotation) {
        // Inicialização se necessária
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return !securityValidator.containsSQLInjection(value);
    }
}
