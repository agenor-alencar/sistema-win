package com.win.win_market.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class SecurityValidator {

    // Padrões para validação
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\+?[1-9]\\d{1,14}$"
    );

    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9\\s._-]+$"
    );

    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
            "(?i)(union|select|insert|update|delete|drop|create|alter|exec|execute|script|iframe|object|embed|form)",
            Pattern.CASE_INSENSITIVE
    );

    // Caracteres perigosos para XSS
    private static final String[] XSS_PATTERNS = {
            "<script", "</script>", "javascript:", "vbscript:", "onload=", "onerror=",
            "onclick=", "onmouseover=", "onfocus=", "onblur=", "onchange=", "onsubmit=",
            "document.cookie", "document.write", "window.location", "eval(", "expression("
    };

    /**
     * Valida se o email está em formato correto
     */
    public boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Valida se o telefone está em formato correto
     */
    public boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * Verifica se a string contém apenas caracteres alfanuméricos seguros
     */
    public boolean isAlphanumericSafe(String input) {
        return input != null && ALPHANUMERIC_PATTERN.matcher(input).matches();
    }

    /**
     * Detecta possíveis tentativas de SQL Injection
     */
    public boolean containsSQLInjection(String input) {
        if (input == null) return false;
        return SQL_INJECTION_PATTERN.matcher(input).find();
    }

    /**
     * Detecta possíveis tentativas de XSS
     */
    public boolean containsXSS(String input) {
        if (input == null) return false;

        String lowerInput = input.toLowerCase();
        for (String pattern : XSS_PATTERNS) {
            if (lowerInput.contains(pattern.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sanitiza entrada removendo caracteres perigosos
     */
    public String sanitizeInput(String input) {
        if (input == null) return null;

        // Remove caracteres de controle
        input = input.replaceAll("[\u0000-\u001F\u007F-\u009F]", "");

        // Limita o tamanho
        if (input.length() > 1000) {
            input = input.substring(0, 1000);
        }

        // Remove espaços extras
        input = input.trim().replaceAll("\\s+", " ");

        return input;
    }

    /**
     * Valida senha forte
     */
    public boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(ch -> "!@#$%^&*()_+-=[]{}|;:,.<>?".indexOf(ch) >= 0);

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    /**
     * Valida se o input está dentro do tamanho permitido
     */
    public boolean isValidLength(String input, int minLength, int maxLength) {
        if (input == null) return false;
        int length = input.length();
        return length >= minLength && length <= maxLength;
    }
}
