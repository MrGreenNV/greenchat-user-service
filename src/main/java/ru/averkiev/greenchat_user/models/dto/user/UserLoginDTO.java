package ru.averkiev.greenchat_user.models.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.averkiev.greenchat_user.models.Status;

import java.util.List;

/**
 * Класс DTO для представления данных для аутентификации пользователя.
 * @author mrGreenNV
 */
@Getter
@Setter
@ResponseBody
public class UserLoginDTO {

    /**
     * Идентификатор пользователя.
     */
    private Long id;

    /**
     * Имя пользователя для входа в систему - логин.
     */
    private String login;

    /**
     * Хэшированный пароль пользователя.
     */
    private String password;

    /**
     * Имя пользователя.
     */
    private String firstname;

    /**
     * Фамилия пользователя.
     */
    private String lastname;

    /**
     * Email пользователя.
     */
    private String email;

    /**
     * Статус пользователя в системе.
     */
    private Status status;

    /**
     * Список ролей пользователя.
     */
    private List<String> roles;

}
