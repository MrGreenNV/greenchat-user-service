package ru.averkiev.greenchat_user.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.greenchat_user.exceptions.RegistrationException;
import ru.averkiev.greenchat_user.models.User;
import ru.averkiev.greenchat_user.models.dto.user.UserAuthDTO;
import ru.averkiev.greenchat_user.models.dto.user.UserCreateDTO;
import ru.averkiev.greenchat_user.models.dto.user.UserRegistrationDTO;
import ru.averkiev.greenchat_user.services.impl.UserServiceImpl;
import ru.averkiev.greenchat_user.utils.CustomModelMapper;

/**
 * @author mrGreenNV
 */
@RestController
@RequestMapping("greenchat/users")
@RequiredArgsConstructor
public class UserController {

    /**
     * Сервис для управления пользователями.
     */
    private final UserServiceImpl userService;

    private final CustomModelMapper modelMapper;

    /**
     * API-endpoint для регистрации нового пользователя.
     * @param userCreateDTO данные для регистрации нового пользователя.
     * @return данные зарегистрированного пользователя или null.
     */
    @PostMapping("register")
    public ResponseEntity<UserRegistrationDTO> register(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        try {
            return ResponseEntity.ok(userService.register(userCreateDTO));
        } catch (RegistrationException regEx) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("{username}")
    public ResponseEntity<UserAuthDTO> getUser(@PathVariable String username) {
        User user = userService.getUserByLogin(username).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserAuthDTO userAuthDTO = modelMapper.map(user, UserAuthDTO.class);

        return ResponseEntity.ok(userAuthDTO);
    }

}
