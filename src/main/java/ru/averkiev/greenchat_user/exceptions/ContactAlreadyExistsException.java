package ru.averkiev.greenchat_user.exceptions;

/**
 * Исключение, выбрасываемое в случае если контакт уже существует в базе данных.
 * @author mrGreenNV
 */
public class ContactAlreadyExistsException extends RuntimeException {

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public ContactAlreadyExistsException(String msg) {
        super(msg);
    }

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке и причиной.
     * @param msg - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public ContactAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
