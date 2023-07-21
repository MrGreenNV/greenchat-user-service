package ru.averkiev.greenchat_user.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Класс представляет собой валидатор для проверки логина пользователя.
 * @author mrGreenNV
 */
public class CustomLoginValidation implements ConstraintValidator<CustomLogin, String> {

    /**
     * Регулярное выражение для валидного логина.
     */
    private final static Pattern LOGIN_PATTERN = Pattern.compile("^[a-zA-Z0-9]*_?[a-zA-Z0-9]*$");

    /**
     * Минимальное количество символов в логине.
     */
    private int min;

    /**
     * Максимальное количество символов в логине.
     */
    private int max;

    /**
     * Инициализирует валидатор, устанавливая граничные значения.
     * @param constraintAnnotation аннотация
     */
    @Override
    public void initialize(CustomLogin constraintAnnotation) {
        this.min = 6;
        this.max = 254;
    }

    /**
     * Проверяет валидность логина.
     * @param value логин.
     * @param context контекст.
     * @return true если логин валиден, иначе false
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        int length = value.length();

        return length >= min && length <= max && LOGIN_PATTERN.matcher(value).matches();
    }
}
