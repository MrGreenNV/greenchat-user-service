package ru.averkiev.greenchat_user.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Класс представляет собой валидатор для проверки имени, фамилии или отчества пользователя.
 * @author mrGreenNV
 */
public class CustomNameValidation implements ConstraintValidator<CustomName, String> {

    /**
     * Регулярное выражение для проверки строки.
     */
    private final static Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z]*$|^[а-яА-Я]*$");

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
    public void initialize(CustomName constraintAnnotation) {
        this.min = 3;
        this.max = 99;
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

        int length = value.length();

        return length >= min && length <= max && NAME_PATTERN.matcher(value).matches();
    }
}
