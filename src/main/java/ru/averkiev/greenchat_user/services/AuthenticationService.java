package ru.averkiev.greenchat_user.services;

/**
 * Класс представляет собой функциональность для проверки аутентификации пользователя.
 * @author mrGreenNV
 */
public interface AuthenticationService {

    /**
     * Проверяет токен.
     * @param token проверяемый токен.
     * @return true, если токен действительный иначе false.
     */
    boolean validateToken(String token);
}
