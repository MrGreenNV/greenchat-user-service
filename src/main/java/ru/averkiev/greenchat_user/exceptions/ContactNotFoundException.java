package ru.averkiev.greenchat_user.exceptions;

/**
 * Исключение, выбрасываемое в случае если не удалось найти контакт в базе данных.
 * @author mrGreenNV
 */
public class ContactNotFoundException extends RuntimeException {

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public ContactNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке и причиной.
     * @param msg - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public ContactNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
