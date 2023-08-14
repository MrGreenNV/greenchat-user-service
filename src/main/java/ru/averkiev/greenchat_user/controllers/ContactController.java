package ru.averkiev.greenchat_user.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.greenchat_user.exceptions.UserNotFoundException;
import ru.averkiev.greenchat_user.models.Contact;
import ru.averkiev.greenchat_user.models.User;
import ru.averkiev.greenchat_user.services.impl.ContactServiceImpl;
import ru.averkiev.greenchat_user.services.impl.UserServiceImpl;

import java.util.List;

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
     * API-endpoint позволяющий создать новый контакт.
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

    @DeleteMapping("{contactId}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable Long userId, @PathVariable Long contactId) {

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Contact>> showUserContacts(@PathVariable Long userId) {

        return ResponseEntity.ok(null);
    }
}
