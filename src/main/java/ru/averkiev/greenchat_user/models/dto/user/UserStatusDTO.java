package ru.averkiev.greenchat_user.models.dto.user;

import lombok.Getter;
import lombok.Setter;
import ru.averkiev.greenchat_user.models.Status;

/**
 * Класс DTO для представления статуса пользователя.
 * @author mrGreenNV
 */
@Getter
@Setter
public class UserStatusDTO {

    /**
     * Идентификатор пользователя.
     */
    private Long id;


    /**
     * Статус пользователя в системе.
     */
    private Status status;

}