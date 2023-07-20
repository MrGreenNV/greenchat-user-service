package ru.averkiev.greenchat_user.exceptions;

/**
 * Исключение, выбрасываемое в случае если блокировка уже существует в базе данных.
 * @author mrGreenNV
 */
public class BlockingAlreadyExistsException extends RuntimeException {

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public BlockingAlreadyExistsException(String msg) {
        super(msg);
    }

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке и причиной.
     * @param msg - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public BlockingAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
