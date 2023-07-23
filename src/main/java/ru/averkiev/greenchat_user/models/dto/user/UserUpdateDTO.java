package ru.averkiev.greenchat_user.models.dto.user;

import lombok.Getter;
import lombok.Setter;
import ru.averkiev.greenchat_user.validation.CustomEmail;
import ru.averkiev.greenchat_user.validation.CustomLogin;
import ru.averkiev.greenchat_user.validation.CustomName;

/**
 * Класс DTO для обновления информации о пользователе.
 * Модель используется для передачи данных между различными слоями приложения.
 * @author mrGreenNV
 */
@Getter
@Setter
public class UserUpdateDTO {

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