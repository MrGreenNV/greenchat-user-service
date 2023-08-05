package ru.averkiev.greenchat_user.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.averkiev.greenchat_user.services.AuthenticationService;

/**
 * Класс представляет собой реализацию функциональности проверки аутентификации пользователя с помощью запроса к
 * микросервису аутентификации.
 * @author mrGreenNV
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * URL микросервиса аутентификации и авторизации.
     */
    private final String authServiceUrl;

    /**
     * Конструктор получает значение из файла и назначает URL микросервиса аутентификации и авторизации.
     * @param authServiceUrl URL микросервиса аутентификации и авторизации.
     */
    public AuthenticationServiceImpl(@Value("${authentication.service.url}") String authServiceUrl) {
        this.authServiceUrl = authServiceUrl;
    }

    /**
     * Отправляет запрос в микросервис аутентификации и авторизации для валидации токена.
     * @param token проверяемый токен.
     * @return true если проверка прошла успешна, иначе false.
     */
    @Override
    public boolean validateToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String > entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Void> response = new RestTemplate().exchange(
                    authServiceUrl + "/validate",
                    HttpMethod.GET,
                    entity,
                    Void.class
            );
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            return false;
        }
    }
}
