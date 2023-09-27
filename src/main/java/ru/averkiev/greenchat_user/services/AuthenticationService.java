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

    /**
     * Проверяет приближение срока окончания действия токена.
     * @param token проверяемы токен.
     * @return true если токен скоро закончится или уже закончился иначе false.
     */
    boolean isAccessTokenExpired(String token);

    /**
     * Обновляет пару токенов - refresh и access.
     * @param token refresh токен
     * @return true если токены успешно обновлены, иначе false.
     */
    boolean refreshAccessToken(String token);

    /**
     * Обновляет access токен.
     * @param token refresh токен.
     * @return обновленный access токен.
     */
    String getUpdatedAccessToken(String token);
}
