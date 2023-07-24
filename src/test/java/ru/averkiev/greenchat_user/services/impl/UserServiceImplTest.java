package ru.averkiev.greenchat_user.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import ru.averkiev.greenchat_user.exceptions.*;
import ru.averkiev.greenchat_user.models.Status;
import ru.averkiev.greenchat_user.models.User;
import ru.averkiev.greenchat_user.models.dto.user.UserCreateDTO;
import ru.averkiev.greenchat_user.models.dto.user.UserRegistrationDTO;
import ru.averkiev.greenchat_user.models.dto.user.UserUpdateDTO;
import ru.averkiev.greenchat_user.models.dto.user.UserUpdatePasswordDTO;
import ru.averkiev.greenchat_user.repositories.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Тестовый класс для проверки класса UserServiceImpl, реализующего функционал по взаимодействию класса User с базой
 * данных.
 * @author mrGreenNV
 */
class UserServiceImplTest {

    @Mock
    private ModelMapper modelMapper;

    // Установка заглушки.
    @Mock
    private UserRepository userRepository;

    // Внедрение userRepository в сервис.
    @InjectMocks
    private UserServiceImpl userService;

    // Выполнение метода перед каждым тестом.
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Проверяет верную работу метода по регистрации нового пользователя с валидными данными.
     */
    @Test
    void testRegister_ValidUser_ReturnRegisteredUser() {
        // Создание тестовых данных.
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setLogin("test_user");
        userCreateDTO.setPassword("test_password");
        userCreateDTO.setConfirmPassword("test_password");
        userCreateDTO.setFirstname("testFirstname");
        userCreateDTO.setLastname("testLastname");
        userCreateDTO.setEmail("test@gmail.com");

        User user = new User();
        user.setLogin("test_user");
        user.setPassword("test_password");
        user.setFirstname("testFirstname");
        user.setLastname("testLastname");
        user.setEmail("test@gmail.com");

        UserRegistrationDTO expectedResponse = new UserRegistrationDTO();
        expectedResponse.setLogin("test_user");
        expectedResponse.setFirstname("testFirstname");
        expectedResponse.setLastname("testLastname");
        expectedResponse.setEmail("test@gmail.com");

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(userCreateDTO, User.class)).thenReturn(user);
        when(modelMapper.map(user, UserRegistrationDTO.class)).thenReturn(expectedResponse);

        // вызов тестируемого метода.
        UserRegistrationDTO result = userService.register(userCreateDTO);

        // Проверка результатов.
        assertNotNull(result);
        assertEquals(expectedResponse, result);
        verify(userRepository, times(1)).save(any(User.class));

    }

    /**
     * Проверяет верную работу метода по регистрации нового пользователя с дублированием логина.
     */
    @Test
    void testRegister_DuplicateLogin_ThrowUserWithLoginAlreadyExistsException() {

        // Создание тестовых данных.
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setLogin("test_user");
        userCreateDTO.setPassword("test_password");
        userCreateDTO.setConfirmPassword("test_password");
        userCreateDTO.setFirstname("testFirstname");
        userCreateDTO.setLastname("testLastname");
        userCreateDTO.setEmail("test@gmail.com");

        User user = new User();
        user.setLogin("test_user");
        user.setPassword("test_password");
        user.setFirstname("testFirstname");
        user.setLastname("testLastname");
        user.setEmail("test@gmail.com");

        when(userRepository.findByLogin(user.getLogin())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(userCreateDTO, User.class)).thenReturn(user);

        // вызов тестируемого метода.
        RegistrationException result = assertThrows(RegistrationException.class, () -> userService.register(userCreateDTO));

        // Проверка результатов.
        assertEquals("Данный логин уже зарегистрирован", result.getMessage());
        assertEquals(UserWithLoginAlreadyExistsException.class, result.getCause().getClass());

    }

    /**
     * Проверяет верную работу метода по регистрации нового пользователя с дублированием email.
     */
    @Test
    void testRegister_DuplicateEmail_ThrowUserWithEmailAlreadyExistsException() {

        // Создание тестовых данных.
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setLogin("test_user");
        userCreateDTO.setPassword("test_password");
        userCreateDTO.setConfirmPassword("test_password");
        userCreateDTO.setFirstname("testFirstname");
        userCreateDTO.setLastname("testLastname");
        userCreateDTO.setEmail("test@gmail.com");

        User user = new User();
        user.setLogin("test_user");
        user.setPassword("test_password");
        user.setFirstname("testFirstname");
        user.setLastname("testLastname");
        user.setEmail("test@gmail.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(userCreateDTO, User.class)).thenReturn(user);

        // вызов тестируемого метода.
        RegistrationException result = assertThrows(RegistrationException.class, () -> userService.register(userCreateDTO));

        // Проверка результатов.
        assertEquals("Данный email уже зарегистрирован", result.getMessage());
        assertEquals(UserWithEmailAlreadyExistsException.class, result.getCause().getClass());

    }

    /**
     * Проверяет верную работу метода по регистрации нового пользователя с неверным подтверждением пароля.
     */
    @Test
    void testRegister_InvalidConfirmPassword_ThrowPasswordsNotMathException() {

        // Создание тестовых данных.
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setLogin("test_user");
        userCreateDTO.setPassword("test_password");
        userCreateDTO.setConfirmPassword("another_password");
        userCreateDTO.setFirstname("testFirstname");
        userCreateDTO.setLastname("testLastname");
        userCreateDTO.setEmail("test@gmail.com");

        User user = new User();
        user.setLogin("test_user");
        user.setPassword("test_password");
        user.setFirstname("testFirstname");
        user.setLastname("testLastname");
        user.setEmail("test@gmail.com");

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(userCreateDTO, User.class)).thenReturn(user);

        // вызов тестируемого метода.
        RegistrationException result = assertThrows(RegistrationException.class, () -> userService.register(userCreateDTO));

        // Проверка результатов.
        assertEquals("Пароли не совпадают", result.getMessage());
        assertEquals(PasswordsNotMatchException.class, result.getCause().getClass());

    }

    /**
     * Проверяет обновление пользователя, если он был найден в базе данных.
     */
    @Test
    void testUpdateUser_ReturnUserUpdateDTO() {

        // Создание тестовых данных.
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setLogin("new_login");
        userUpdateDTO.setFirstname("newFirstname");
        userUpdateDTO.setLastname("newLastname");
        userUpdateDTO.setEmail("new@gmail.com");

        User user = new User();
        user.setLogin("test_user");
        user.setFirstname("testFirstname");
        user.setLastname("testLastname");
        user.setEmail("test@gmail.com");

        User updateUser = new User();
        updateUser.setLogin("new_login");
        updateUser.setFirstname("newFirstname");
        updateUser.setLastname("newLastname");
        updateUser.setEmail("new@gmail.com");

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(userUpdateDTO, User.class)).thenReturn(updateUser);
        when(modelMapper.map(user, UserUpdateDTO.class)).thenReturn(userUpdateDTO);

        // Вызов тестируемого метода.
        UserUpdateDTO result = userService.updateUser(1L, userUpdateDTO);

        // Проверка результатов.
        assertNotNull(result);
        assertEquals(userUpdateDTO.getLogin(), user.getLogin());
        assertEquals(userUpdateDTO.getFirstname(), user.getFirstname());
        assertEquals(userUpdateDTO.getLastname(), user.getLastname());
        assertEquals(userUpdateDTO.getEmail(), user.getEmail());
        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).findById(any(Long.class));

    }

    /**
     * Проверяет обновление пользователя, если он не был найден в базе данных.
     */
    @Test
    void testUpdateUser_UserNotFound_ThrowsUserNotFoundException() {

        // Создание тестовых данных.
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setLogin("new_login");
        userUpdateDTO.setFirstname("newFirstname");
        userUpdateDTO.setLastname("newLastname");
        userUpdateDTO.setEmail("new@gmail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Вызов тестируемого метода.
        UserNotFoundException result = assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, userUpdateDTO));

        // Проверка результатов.
        verify(userRepository, times(1)).findById(any(Long.class));
        assertEquals("Пользователь с id: " + 1L + " не найден", result.getMessage());
        assertEquals(UserNotFoundException.class, result.getClass());

    }

    /**
     * Проверяет обновление валидного пароля пользователя.
     */
    @Test
    void testUpdateUserPassword_ValidPassword() {

        // Создание тестовых данных.
        Long userId = 1L;

        UserUpdatePasswordDTO userUpdatePasswordDTO = new UserUpdatePasswordDTO();
        userUpdatePasswordDTO.setCurrentPassword("current_password");
        userUpdatePasswordDTO.setNewPassword("new_password");
        userUpdatePasswordDTO.setConfirmPassword("new_password");

        User user = new User();
        user.setPassword("current_password");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // Вызов тестируемого метода.
        userService.updateUserPassword(userId, userUpdatePasswordDTO);

        // Проверка результатов.
        assertEquals(userUpdatePasswordDTO.getNewPassword(), user.getPassword());
        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).findById(userId);
    }

    /**
     * Проверяет обновление валидного пароля пользователя, при неверно введенном текущем пароле.
     */
    @Test
    void testUpdateUserPassword_InvalidCurrentPassword_ThrowPasswordNotMatchException() {

        // Создание тестовых данных.
        Long userId = 1L;

        UserUpdatePasswordDTO userUpdatePasswordDTO = new UserUpdatePasswordDTO();
        userUpdatePasswordDTO.setCurrentPassword("current_password");
        userUpdatePasswordDTO.setNewPassword("new_password");
        userUpdatePasswordDTO.setConfirmPassword("new_password");

        User user = new User();
        user.setPassword("invalid_current_password");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // Вызов тестируемого метода.
        PasswordsNotMatchException result = assertThrows(PasswordsNotMatchException.class, () -> userService.updateUserPassword(userId, userUpdatePasswordDTO));

        // Проверка результатов.
        assertNotEquals(userUpdatePasswordDTO.getNewPassword(), user.getPassword());
        assertEquals("Неправильный текущий пароль", result.getMessage());
        assertEquals(PasswordsNotMatchException.class, result.getClass());
        verify(userRepository, times(1)).findById(userId);
    }

    /**
     * Проверяет обновление валидного пароля пользователя, при неверно введенном подтверждении пароля.
     */
    @Test
    void testUpdateUserPassword_InvalidConfirmPassword_ThrowPasswordNotMatchException() {

        // Создание тестовых данных.
        Long userId = 1L;

        UserUpdatePasswordDTO userUpdatePasswordDTO = new UserUpdatePasswordDTO();
        userUpdatePasswordDTO.setCurrentPassword("current_password");
        userUpdatePasswordDTO.setNewPassword("new_password");
        userUpdatePasswordDTO.setConfirmPassword("invalid_new_password");

        User user = new User();
        user.setPassword("current_password");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // Вызов тестируемого метода.
        PasswordsNotMatchException result = assertThrows(PasswordsNotMatchException.class, () -> userService.updateUserPassword(userId, userUpdatePasswordDTO));

        // Проверка результатов.
        assertNotEquals(userUpdatePasswordDTO.getNewPassword(), user.getPassword());
        assertEquals("Пароль и подтверждение пароля не совпадают", result.getMessage());
        assertEquals(PasswordsNotMatchException.class, result.getClass());
        verify(userRepository, times(1)).findById(userId);
    }

    /**
     * Проверяет обновление валидного пароля пользователя, при попытке сменить пароль на тот же самый.
     */
    @Test
    void testUpdateUserPassword_InvalidNewPassword_ThrowPasswordNotMatchException() {

        // Создание тестовых данных.
        Long userId = 1L;

        UserUpdatePasswordDTO userUpdatePasswordDTO = new UserUpdatePasswordDTO();
        userUpdatePasswordDTO.setCurrentPassword("current_password");
        userUpdatePasswordDTO.setNewPassword("current_password");
        userUpdatePasswordDTO.setConfirmPassword("current_password");

        User user = new User();
        user.setPassword("current_password");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // Вызов тестируемого метода.
        PasswordsNotMatchException result = assertThrows(PasswordsNotMatchException.class, () -> userService.updateUserPassword(userId, userUpdatePasswordDTO));

        // Проверка результатов.
        assertEquals("Новый пароль должен отличаться от текущего", result.getMessage());
        assertEquals(PasswordsNotMatchException.class, result.getClass());
        verify(userRepository, times(1)).findById(userId);
    }

    /**
     * Проверяет количество вызовов метода delete при удалении пользователя.
     */
    @Test
    void deleteUser() {
        // Создание тестовых данных.

        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Вызов тестируемого метода.
        userService.deleteUser(user.getId());

        // Проверка результатов.
        verify(userRepository, times(1)).delete(user);
    }

    /**
     * Проверяет изменение статуса пользователя при "мягком" удалении.
     */
    @Test
    void softDeleteUser() {

        // Создание тестовых данных.
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setStatus(Status.ACTIVE);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Вызов тестируемого метода.
        userService.softDeleteUser(userId);

        // Проверка результатов.
        assertEquals(Status.DELETED, user.getStatus());
        verify(userRepository, never()).delete(user);
    }
}