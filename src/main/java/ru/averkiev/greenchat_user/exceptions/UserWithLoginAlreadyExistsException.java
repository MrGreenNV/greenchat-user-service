package ru.averkiev.greenchat_user.exceptions;

/**
 * Исключение, выбрасываемое в случае если пользователь с таким именем уже существует.
 * @author mrGreenNV
 */
public class UserWithLoginAlreadyExistsException extends RuntimeException{

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public UserWithLoginAlreadyExistsException(String msg) {
        super(msg);
    }

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке и причиной.
     * @param msg - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public UserWithLoginAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
