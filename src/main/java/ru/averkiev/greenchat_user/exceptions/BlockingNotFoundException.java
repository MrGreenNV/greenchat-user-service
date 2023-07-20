package ru.averkiev.greenchat_user.exceptions;

/**
 * Исключение, выбрасываемое в случае если не удалось найти блокировку в базе данных.
 * @author mrGreenNV
 */
public class BlockingNotFoundException extends RuntimeException {

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public BlockingNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке и причиной.
     * @param msg - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public BlockingNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
