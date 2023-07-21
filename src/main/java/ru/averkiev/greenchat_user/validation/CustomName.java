package ru.averkiev.greenchat_user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Аннотация для валидации имени, фамилии или отчества.
 * @author mrGreenNV
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CustomNameValidation.class)
public @interface CustomName {

    /**
     * Определяет сообщение, которое будет отображаться при нарушении валидации.
     * @return сообщение.
     */
    String message() default "Строка должна быть от 2 до 100 символов. Допускается использовать только буквы";

    /**
     * Определяет группы ограничений, которым будет принадлежать аннотация.
     * @return группы ограничений.
     */
    Class<?>[] groups() default {};

    /**
     * Определяет нагрузку (payload) для аннотации, которая может быть использована для передачи дополнительной
     * информации в процессе валидации.
     * @return нагрузка для аннотации.
     */
    Class<? extends Payload>[] payload() default {};
}
