package com.win.win_market.validation;

import com.win.win_market.util.SecurityValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NoXSSValidator implements ConstraintValidator<NoXSS, String> {

    @Autowired
    private SecurityValidator securityValidator;

    @Override
    public void initialize(NoXSS constraintAnnotation) {
        // Inicialização se necessária
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values são tratados por @NotNull se necessário
        }

        // Verifica XSS e SQL Injection
        return !(securityValidator.containsXSS(value) || securityValidator.containsSQLInjection(value));
    }
}
