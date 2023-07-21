package ru.averkiev.greenchat_user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Аннотация для валидации логина.
 * @author mrGreenNV
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CustomLoginValidation.class)
public @interface CustomLogin {

    /**
     * Определяет сообщение, которое будет отображаться при нарушении валидации.
     * @return сообщение.
     */
    String message() default "Логин должен быть от 5 до 255 символов. Допускается использовать латинские буквы цифры и один символ '_'";

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
