package ru.averkiev.greenchat_user.exceptions;

/**
 * Исключение, выбрасываемое в случае если пароль не совпадает с подтверждением.
 * @author mrGreenNV
 */
public class PasswordsNotMatchException extends RuntimeException {

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public PasswordsNotMatchException(String msg) {
        super(msg);
    }

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке и причиной.
     * @param msg - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public PasswordsNotMatchException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
