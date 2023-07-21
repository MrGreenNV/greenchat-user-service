package ru.averkiev.greenchat_user.models.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс представляет собой модель DTO для создания нового пользователя.
 * Модель используется для передачи данных между различными слоями приложения.
 * @author mrGreenNV
 */
@Getter
@Setter
public class UserCreateDTO {

    /**
     * Имя пользователя для входа в систему - логин.
     */
    @Size(min = 6, max = 254, message = "Логин должен иметь больше 5 и меньше 255 символов")
    @Pattern(regexp = "^[a-zA-Z0-9]*_?[a-zA-Z0-9]*$", message = "В логине допустимы цифры, латинские буквы и один символ '_'")
    private String login;

    /**
     * Хэшированный пароль пользователя.
     */
    private String password;

    /**
     * Имя пользователя.
     */
    @Size(min = 3, max = 99, message = "Имя должно иметь больше 2 и меньше 100 символов")
    @Pattern(regexp = "^[a-zA-Z]*$|^[а-яА-Я]*$", message = "В имени допустимы только буквы")
    private String firstname;

    /**
     * Фамилия пользователя.
     */
    @Size(min = 3, max = 99, message = "Фамилия должна иметь больше 2 и меньше 100 символов")
    @Pattern(regexp = "^[a-zA-Z]*$|^[а-яА-Я]*$", message = "В фамилии допустимы только буквы")
    private String lastname;

    /**
     * Email пользователя.
     */
    @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+.+.[A-Za-z]{2,4}$", message = "Email должен быть валидным")
    private String email;
}