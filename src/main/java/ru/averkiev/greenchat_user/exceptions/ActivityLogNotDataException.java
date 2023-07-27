package ru.averkiev.greenchat_user.exceptions;

/**
 * Исключение, выбрасываемое в случае если запись для сохранения содержит недостаточно данных.
 * @author mrGreenNV
 */
public class ActivityLogNotDataException extends RuntimeException {

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public ActivityLogNotDataException(String msg) {
        super(msg);
    }

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке и причиной.
     * @param msg - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public ActivityLogNotDataException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
