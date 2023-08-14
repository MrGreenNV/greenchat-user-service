package ru.averkiev.greenchat_user.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.greenchat_user.exceptions.UserNotFoundException;
import ru.averkiev.greenchat_user.models.User;
import ru.averkiev.greenchat_user.models.dto.contact.ContactDTO;
import ru.averkiev.greenchat_user.services.impl.ContactServiceImpl;
import ru.averkiev.greenchat_user.services.impl.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс представляет собой контроллер для доступа и изменению информации о пользователях.
 * @author mrGreenNV
 */
@RestController
@RequestMapping("greenchat/users/{userId}/contacts")
@RequiredArgsConstructor
@EnableMethodSecurity
public class ContactController {

    /**
     * Сервис для управления контактами.
     */
    private final ContactServiceImpl contactService;

    /**
     * Сервис для управления Пользователями.
     */
    private final UserServiceImpl userService;

    /**
     * Для приведения классов к DTO
     */
    private final ModelMapper modelMapper;

    /**
     * API-endpoint позволяет создать новый контакт.
     * @param userId идентификатор пользователя, для которого создаётся контакт.
     * @param contactLogin идентификатор пользователя, являющегося будущим контактом.
     * @return статус запроса.
     */
    @PostMapping()
    public ResponseEntity<HttpStatus> createContact(@PathVariable Long userId, @RequestBody String contactLogin) {
        try {

            User contactUser = userService.getUserByLogin(contactLogin)
                    .orElseThrow(() -> new UserNotFoundException("Пользователь с логином: " + contactLogin + " не найден."));

            User user = userService.getUserById(userId)
                    .orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId + " не найден."));

            contactService.createContact(user, contactUser);

            return ResponseEntity.ok(HttpStatus.OK);

        } catch (UserNotFoundException unfEx) {
            unfEx.printStackTrace();
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * API-endpoint позволяет удалить контакт по идентификатору пользователя и удаляемого контакта.
     * @param userId идентификатор пользователя.
     * @param contactId идентификатор удаляемого контакта.
     * @return статус запроса.
     */
    @DeleteMapping("{contactId}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable Long userId, @PathVariable Long contactId) {
        try {

            User contactUser = userService.getUserById(contactId)
                    .orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + contactId + " не найден."));

            User user = userService.getUserById(userId)
                    .orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId + " не найден."));

            contactService.deleteContactByUser(user, contactUser);

            return ResponseEntity.ok(HttpStatus.OK);

        } catch (UserNotFoundException unfEx) {
            unfEx.printStackTrace();
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * API-endpoint позволяет получить список контактов пользователя.
     * @param userId идентификатор пользователя, для которого выводится список контактов.
     * @return статус запроса.
     */
    @GetMapping()
    public ResponseEntity<List<ContactDTO>> showUserContacts(@PathVariable Long userId) {

        List<ContactDTO> contacts = contactService.getContactByUser(userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId + " не найден."))).stream()
                .map(contact -> modelMapper.map(contact, ContactDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(contacts);
    }
}
