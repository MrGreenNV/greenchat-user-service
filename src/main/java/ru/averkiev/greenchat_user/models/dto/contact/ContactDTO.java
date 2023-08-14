package ru.averkiev.greenchat_user.models.dto.contact;

import lombok.Getter;
import lombok.Setter;
import ru.averkiev.greenchat_user.models.dto.user.UserContactDTO;

/**
 * Класс представляет собой DTO для отображения информации в списке контактов пользователя.
 * @author mrGreenNV
 */
@Getter
@Setter
public class ContactDTO {

    /**
     * Контакт пользователя.
     */
    private UserContactDTO contact;
}
