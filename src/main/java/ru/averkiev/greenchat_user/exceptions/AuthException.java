package ru.averkiev.greenchat_user.exceptions;

/**
 * Класс представляет собой исключение, которое возникает в случае, когда Authentication авен null.
 * @author mrGreenNV
 */
public class AuthException extends RuntimeException {
    public AuthException(String msg) {
        super(msg);
    }
}
