package ru.averkiev.greenchat_user.models.dto.user;

import lombok.Getter;
import lombok.Setter;
import ru.averkiev.greenchat_user.validation.CustomEmail;
import ru.averkiev.greenchat_user.validation.CustomLogin;
import ru.averkiev.greenchat_user.validation.CustomName;

/**
 * Класс  DTO для создания нового пользователя.
 * Модель используется для передачи данных между различными слоями приложения.
 * @author mrGreenNV
 */
@Getter
@Setter
public class UserCreateDTO {

    /**
     * Имя пользователя для входа в систему - логин.
     */
    @CustomLogin()
    private String login;

    /**
     * Хэшированный пароль пользователя.
     */
    private String password;

    /**
     * Хэшированное подтверждение пароля пользователя.
     */
    private String ConfirmPassword;

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
