package ru.averkiev.greenchat_user.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.greenchat_user.models.User;
import ru.averkiev.greenchat_user.models.dto.contact.ContactDTO;
import ru.averkiev.greenchat_user.services.ContactService;
import ru.averkiev.greenchat_user.services.UserService;

import java.util.List;

/**
 * Класс представляет собой контроллер для доступа и изменению информации о пользователях.
 * @author mrGreenNV
 */
@RestController
@RequestMapping("greenchat/users-service/v1/users/{userId}/contacts")
@RequiredArgsConstructor
@EnableMethodSecurity
public class ContactController {

    /**
     * Сервис для управления контактами.
     */
    private final ContactService contactService;

    /**
     * Сервис для управления Пользователями.
     */
    private final UserService userService;

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
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping()
    public ResponseEntity<HttpStatus> createContact(@PathVariable Long userId, @RequestBody String contactLogin) {

        User contactUser = userService.getUserByLogin(contactLogin);
        User user = userService.getUserById(userId);
        contactService.createContact(user, contactUser);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * API-endpoint позволяет удалить контакт по идентификатору пользователя и удаляемого контакта.
     * @param userId идентификатор пользователя.
     * @param contactId идентификатор удаляемого контакта.
     * @return статус запроса.
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("{contactId}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable Long userId, @PathVariable Long contactId) {

        User contactUser = userService.getUserById(contactId);
        User user = userService.getUserById(userId);
        contactService.deleteContactByUser(user, contactUser);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * API-endpoint позволяет получить список контактов пользователя.
     * @param userId идентификатор пользователя, для которого выводится список контактов.
     * @return статус запроса.
     */
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<ContactDTO>> showUserContacts(@PathVariable Long userId) {

        return ResponseEntity.ok(contactService.getContactByUser(userService.getUserById(userId)).stream()
                .map(contact -> modelMapper.map(contact, ContactDTO.class)).toList());
    }
}
