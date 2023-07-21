package ru.averkiev.greenchat_user.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Класс представляет собой валидатор для проверки электронной почты.
 * @author mrGreenNV
 */
public class CustomEmailValidation implements ConstraintValidator<CustomEmail, String> {

    /**
     * Регулярное выражение для проверки строки.
     */
    private final static Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+.+.[A-Za-z]{2,4}$");

    /**
     * Инициализирует валидатор, устанавливая граничные значения.
     * @param constraintAnnotation аннотация
     */
    @Override
    public void initialize(CustomEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Проверяет валидность строки.
     * @param value проверяемая строка.
     * @param context контекст.
     * @return true если строка валидна, иначе false
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        return EMAIL_PATTERN.matcher(value).matches();
    }
}
