package ru.averkiev.greenchat_user.models.dto.user;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс DTO для представления данных при смене пароля пользователя.
 * @author mrGreenNV
 */
@Getter
@Setter
public class UserUpdatePasswordDTO {

    /**
     * Текущий пароль.
     */
    private String currentPassword;

    /**
     * Новый пароль.
     */
    private String newPassword;

    /**
     * Подтверждение нового пароля.
     */
    private String confirmPassword;
}
