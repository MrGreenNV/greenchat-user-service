package ru.averkiev.greenchat_user.exceptions;

/**
 * Исключение, выбрасываемое в случае если роль уже существует в базе данных.
 * @author mrGreenNV
 */
public class RoleAlreadyExistsException extends RuntimeException {
    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public RoleAlreadyExistsException(String msg) {
        super(msg);
    }

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке и причиной.
     * @param msg - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public RoleAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
