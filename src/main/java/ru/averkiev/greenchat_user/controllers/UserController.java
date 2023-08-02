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

    /**
     * API-endpoint для взаимодействия с микросервисом аутентификации и авторизации с целью проверки данных
     * пользователя перед выдачей access и refresh токенов.
     * @param username имя пользователя, для которого выполняется проверка.
     * @return DTO пользователя с данными, необходимыми для проверки.
     */
    @GetMapping("{username}")
    public ResponseEntity<UserAuthDTO> getUser(@PathVariable String username) {

        // Получение пользователя по логину.
        User user = userService.getUserByLogin(username).orElse(null);

        // Формирование ответа в случае, когда пользователь не был найден.
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Создание DTO пользователя для передачи сервису аутентификации.
        UserAuthDTO userAuthDTO = modelMapper.map(user, UserAuthDTO.class);

        return ResponseEntity.ok(userAuthDTO);
    }

}
