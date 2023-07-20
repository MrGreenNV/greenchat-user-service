package ru.averkiev.greenchat_user.exceptions;

/**
 * Исключение, выбрасываемое в случае если не удалось найти запись об активности в базе данных.
 * @author mrGreenNV
 */
public class ActivityLogNotFoundException extends RuntimeException {

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public ActivityLogNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке и причиной.
     * @param msg - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public ActivityLogNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
