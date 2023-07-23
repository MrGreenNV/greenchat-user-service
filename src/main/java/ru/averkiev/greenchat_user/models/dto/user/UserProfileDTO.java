package ru.averkiev.greenchat_user.models.dto.user;

import lombok.Getter;
import lombok.Setter;
import ru.averkiev.greenchat_user.validation.CustomEmail;
import ru.averkiev.greenchat_user.validation.CustomLogin;
import ru.averkiev.greenchat_user.validation.CustomName;

/**
 * Класс DTO для представления профиля пользователя.
 * Модель используется для передачи данных между различными слоями приложения.
 * @author mrGreenNV
 */
@Getter
@Setter
public class UserProfileDTO {

    /**
     * Идентификатор пользователя.
     */
    private Long id;

    /**
     * Имя пользователя для входа в систему - логин.
     */
    @CustomLogin()
    private String login;

    /**
     * Имя пользователя.
     */
    @CustomName
    private String firstname;

    /**
     * Фамилия пользователя.
     */
    @CustomName
    private String lastname;

    /**
     * Email пользователя.
     */
    @CustomEmail
    private String email;
}
