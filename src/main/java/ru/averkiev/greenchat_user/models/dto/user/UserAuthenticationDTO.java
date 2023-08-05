package ru.averkiev.greenchat_user.models.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Класс DTO для представления данных при аутентификации пользователя в системе.
 * @author mrGreenNV
 */
@Getter
@Setter
@ResponseBody
public class UserAuthenticationDTO {

    /**
     * Имя пользователя для входа в систему - логин.
     */
    private String login;

    /**
     * Хэшированный пароль пользователя.
     */
    private String password;

    /**
     * Список ролей пользователя.
     */
    private List<String> roles;
}