package ru.averkiev.greenchat_user.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.greenchat_user.exceptions.RegistrationException;
import ru.averkiev.greenchat_user.exceptions.UserNotFoundException;
import ru.averkiev.greenchat_user.models.User;
import ru.averkiev.greenchat_user.models.dto.user.*;
import ru.averkiev.greenchat_user.services.impl.UserServiceImpl;
import ru.averkiev.greenchat_user.utils.CustomModelMapper;

import java.util.List;

/**
 * Класс представляет собой контроллер для доступа и изменению информации о пользователях.
 * @author mrGreenNV
 */
@RestController
@RequestMapping("greenchat/users")
@RequiredArgsConstructor
@EnableMethodSecurity
public class UserController {

    /**
     * Сервис для управления пользователями.
     */
    private final UserServiceImpl userService;

    /**
     * Кастомный ModelMapper.
     */
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
    public ResponseEntity<UserLoginDTO> getUserByLogin(@PathVariable String username) {
        // Получение пользователя по логину.
        User user = userService.getUserByLogin(username).orElse(null);

        // Формирование ответа в случае, когда пользователь не был найден.
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Создание DTO пользователя для передачи сервису аутентификации.
        UserLoginDTO userLoginDTO = modelMapper.map(user, UserLoginDTO.class);

        return ResponseEntity.ok(userLoginDTO);
    }

    /**
     * API-endpoint для обновления сведений о пользователе.
     * @param userId идентификатор обновляемого пользователя.
     * @param userUpdateDTO DTO пользователя с данными для обновления.
     * @return DTO с обновлёнными данными.
     */
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("{userId}")
    public ResponseEntity<UserUpdateDTO> updateUser(@PathVariable Long userId,
                                                    @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return ResponseEntity.ok(userService.updateUser(userId, userUpdateDTO));
    }

    /**
     * API-endpoint для удаления пользователя из базы данных.
     * @param userId идентификатор удаляемого пользователя.
     * @return статус удаления пользователя.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (UserNotFoundException unfEx) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * API-endpoint для "мягкого" удаления пользователя из базы данных.
     * @param userId идентификатор удаляемого пользователя.
     * @return статус удаления пользователя.
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("{userId}/soft")
    public ResponseEntity<HttpStatus> softDeleteUser(@PathVariable Long userId) {
        try {
            userService.softDeleteUser(userId);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (UserNotFoundException unfEx) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * API-endpoint для получения информации о пользователе.
     * @param userId идентификатор пользователя.
     * @return DTO пользователя с данными профиля.
     */
    @GetMapping("{userId}/profile")
    public ResponseEntity<UserProfileDTO> showUserProfile(@PathVariable Long userId) {
        UserProfileDTO userProfileDTO = modelMapper.map(userService.getUserById(userId), UserProfileDTO.class);

        if (userProfileDTO != null) {
            return ResponseEntity.ok(userProfileDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserProfileDTO>> showAllUsers() {
        List<UserProfileDTO> userProfileDTOList = userService.getAllUsers().stream()
                .map(user -> modelMapper.map(user, UserProfileDTO.class))
                .toList();

        return ResponseEntity.ok(userProfileDTOList);
    }
}
