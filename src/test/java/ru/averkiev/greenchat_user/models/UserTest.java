package ru.averkiev.greenchat_user.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для User.
 * @author mrGreenNV
 */
class UserTest {

    /**
     * Объявление Validator для тестирования.
     */
    private static Validator validator;

    /**
     * Объявление User для тестирования.
     */
    private static User user;

    /**
     * Создание объекта Validator перед тестами.
     */
    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Создание User перед каждым тестом.
     */
    @BeforeEach
    public void setUser() {
        user = new User();
    }

    /**
     * Проверяет установку логина с валидной длиной.
     */
    @Test
    public void ValidateLoginLength_ValidLength() {
        // Создание тестовых данных.
        user.setLogin("long_login");
        // Вызов тестируемого метода.
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // Проверка результатов.
        assertTrue(violations.isEmpty());
    }

    /**
     * Проверяет установку логина с невалидной длиной.
     */
    @Test
    public void ValidateLoginLength_InvalidLength() {
        // Создание тестовых данных.
        user.setLogin("short");
        // Вызов тестируемого метода.
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // Проверка результатов.
        assertFalse(violations.isEmpty());
        assertEquals("Логин должен иметь больше 5 и меньше 255 символов", violations.iterator().next().getMessage());
    }

    /**
     * Проверяет установку логина с валидными данными.
     */
    @Test
    public void ValidateLoginPattern_ValidPattern() {
        // Создание тестовых данных.
        user.setLogin("valid_login");
        // Вызов тестируемого метода.
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // Проверка результатов.
        assertTrue(violations.isEmpty());
    }

    /**
     * Проверяет установку логина с невалидными данными.
     */
    @Test
    public void ValidateLoginPattern_InvalidPattern() {
        // Создание тестовых данных.
        user.setLogin("invalid_login!");
        // Вызов тестируемого метода.
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // Проверка результатов.
        assertFalse(violations.isEmpty());
        assertEquals("В логине допустимы цифры, латинские буквы и один символ '_'", violations.iterator().next().getMessage());
    }

    /**
     * Проверяет установку имени пользователя с валидной длиной.
     */
    @Test
    public void ValidateFirstnameLength_ValidLength() {
        // Создание тестовых данных.
        user.setFirstname("firstname");
        // Вызов тестируемого метода.
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // Проверка результатов.
        assertTrue(violations.isEmpty());
    }

    /**
     * Проверяет установку имени пользователя с невалидной длиной.
     */
    @Test
    public void ValidateFirstnameLength_InvalidLength() {
        // Создание тестовых данных.
        user.setFirstname("sh");
        // Вызов тестируемого метода.
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // Проверка результатов.
        assertFalse(violations.isEmpty());
        assertEquals("Имя должно иметь больше 2 и меньше 100 символов", violations.iterator().next().getMessage());
    }

    /**
     * Проверяет установку имени пользователя с валидными данными.
     */
    @Test
    public void ValidateFirstnamePattern_ValidPattern() {
        // Создание тестовых данных.
        user.setFirstname("firstname");
        // Вызов тестируемого метода.
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // Проверка результатов.
        assertTrue(violations.isEmpty());
    }

    /**
     * Проверяет установку имени пользователя с невалидными данными.
     */
    @Test
    public void ValidateFirstnamePattern_InvalidPattern() {
        // Создание тестовых данных.
        user.setFirstname("invalid_firstname!");
        // Вызов тестируемого метода.
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // Проверка результатов.
        assertFalse(violations.isEmpty());
        assertEquals("В имени допустимы только буквы", violations.iterator().next().getMessage());
    }

    /**
     * Проверяет установку фамилии пользователя с валидной длиной.
     */
    @Test
    public void ValidateLastnameLength_ValidLength() {
        // Создание тестовых данных.
        user.setLastname("lastname");
        // Вызов тестируемого метода.
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // Проверка результатов.
        assertTrue(violations.isEmpty());
    }

    /**
     * Проверяет установку фамилии пользователя с невалидной длиной.
     */
    @Test
    public void ValidateLastnameLength_InvalidLength() {
        // Создание тестовых данных.
        user.setLastname("sh");
        // Вызов тестируемого метода.
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // Проверка результатов.
        assertFalse(violations.isEmpty());
        assertEquals("Фамилия должна иметь больше 2 и меньше 100 символов", violations.iterator().next().getMessage());
    }

    /**
     * Проверяет установку фамилии пользователя с валидными данными.
     */
    @Test
    public void ValidateLastnamePattern_ValidPattern() {
        // Создание тестовых данных.
        user.setLastname("lastname");
        // Вызов тестируемого метода.
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // Проверка результатов.
        assertTrue(violations.isEmpty());
    }

    /**
     * Проверяет установку фамилии пользователя с невалидными данными.
     */
    @Test
    public void ValidateLastnamePattern_InvalidPattern() {
        // Создание тестовых данных.
        user.setLastname("invalid_lastname1");
        // Вызов тестируемого метода.
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // Проверка результатов.
        assertFalse(violations.isEmpty());
        assertEquals("В фамилии допустимы только буквы", violations.iterator().next().getMessage());
    }

    /**
     * Проверяет установку валидного значения электронной почты пользователя.
     */
    @Test
    public void ValidateEmailPattern_ValidPattern() {
        // Создание тестовых данных.
        user.setEmail("test@gmail.com");
        // Вызов тестируемого метода.
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // Проверка результатов.
        assertTrue(violations.isEmpty());
    }

    /**
     * Проверяет установку невалидного значения электронной почты пользователя.
     */
    @Test
    public void ValidateEmailPattern_InvalidPattern() {
        // Создание тестовых данных.
        user.setEmail("email");
        // Вызов тестируемого метода.
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // Проверка результатов.
        assertFalse(violations.isEmpty());
        assertEquals("Email должен быть валидным", violations.iterator().next().getMessage());
    }
}