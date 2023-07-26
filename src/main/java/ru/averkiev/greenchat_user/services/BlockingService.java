package ru.averkiev.greenchat_user.services;

import ru.averkiev.greenchat_user.exceptions.BlockingAlreadyExistsException;
import ru.averkiev.greenchat_user.exceptions.BlockingNotFoundException;
import ru.averkiev.greenchat_user.exceptions.UserNotFoundException;
import ru.averkiev.greenchat_user.models.Blocking;
import ru.averkiev.greenchat_user.models.User;

import java.util.Optional;

/**
 * Интерфейс определяет функциональность для управления блокировками между пользователями.
 * @author mrGreenNV
 */
public interface BlockingService {

    /**
     * Создает новую блокировку между пользователями.
     * @param user инициатор блокировки.
     * @param blockedUser блокируемый пользователя.
     * @return возвращает объект новой блокировки.
     * @throws BlockingAlreadyExistsException выбрасывает если такая блокировка уже существует.
     */
    Blocking createBlocking(User user, User blockedUser) throws BlockingAlreadyExistsException;

    /**
     * Возвращает блокировку между пользователями по их идентификаторам.
     * @param user инициатор блокировки.
     * @param blockedUser заблокированный пользователя.
     * @return Optional, содержащий найденную блокировку, или пустой Optional, если блокировка не найдена.
     */
    Optional<Blocking> getBlockingByUsers(User user, User blockedUser);

    /**
     * Возвращает блокировку между пользователями по её указанному идентификатору.
     * @param blockingId идентификатор указанной блокировки.
     * @return Optional, содержащий найденную блокировку, или пустой Optional, если блокировка не найдена.
     */
    Optional<Blocking> getBlockingById(Long blockingId);

    /**
     * Удаляет блокировку между пользователями по их идентификаторам.
     * @param user инициатор блокировки.
     * @param blockedUser заблокированный пользователя.
     * @throws BlockingNotFoundException выбрасывается если блокировка не найдена.
     */
    void deleteBlockingByUsers(User user, User blockedUser) throws BlockingNotFoundException;

    /**
     * Удаляет блокировку между пользователями по её указанному идентификатору.
     * @param blockingId идентификатор указанной блокировки
     * @throws BlockingNotFoundException выбрасывает если блокировка не найдена.
     */
    void deleteBlockingById(Long blockingId) throws BlockingNotFoundException;
}
