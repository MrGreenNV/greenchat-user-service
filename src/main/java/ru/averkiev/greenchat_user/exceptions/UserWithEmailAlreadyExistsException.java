package ru.averkiev.greenchat_user.exceptions;

/**
 * Исключение, выбрасываемое в случае если пользователь с таким email уже существует.
 * @author mrGreenNV
 */
public class UserWithEmailAlreadyExistsException extends RuntimeException {

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public UserWithEmailAlreadyExistsException(String msg) {
        super(msg);
    }

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке и причиной.
     * @param msg - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public UserWithEmailAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
