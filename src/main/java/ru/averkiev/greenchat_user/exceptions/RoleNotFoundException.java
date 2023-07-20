package ru.averkiev.greenchat_user.exceptions;

/**
 * Исключение, выбрасываемое в случае если не удалось найти роль пользователя в базе данных.
 * @author mrGreenNV
 */
public class RoleNotFoundException extends RuntimeException {
    
    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public RoleNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке и причиной.
     * @param msg - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public RoleNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
