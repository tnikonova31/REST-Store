package com.example.attestation_security.exception;

/**
 * Исключение используется для ошибок аутентификации и авторизации.
 *
 */
public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
    }

}
