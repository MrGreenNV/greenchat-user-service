package ru.averkiev.greenchat_user.services;

import ru.averkiev.greenchat_user.exceptions.BlockingAlreadyExistsException;
import ru.averkiev.greenchat_user.exceptions.BlockingNotFoundException;
import ru.averkiev.greenchat_user.exceptions.UserNotFoundException;
import ru.averkiev.greenchat_user.models.Blocking;

import java.util.Optional;

/**
 * Интерфейс определяет функциональность для управления блокировками между пользователями.
 * @author mrGreenNV
 */
public interface BlockingService {

    /**
     * Создает новую блокировку между пользователями.
     * @param userId идентификатор инициатора блокировки.
     * @param blockedUserId идентификатор блокируемого пользователя.
     * @return возвращает объект новой блокировки.
     * @throws UserNotFoundException выбрасывает если пользователь с указанным идентификатором не найден.
     * @throws BlockingAlreadyExistsException выбрасывает если такая блокировка уже существует.
     */
    Blocking createBlocking(Long userId, Long blockedUserId) throws UserNotFoundException, BlockingAlreadyExistsException;

    /**
     * Возвращает блокировку между пользователями по их идентификаторам.
     * @param userId идентификатор пользователя инициировавшего блокировку.
     * @param blockedUserId идентификатор заблокированного пользователя.
     * @return Optional, содержащий найденную блокировку, или пустой Optional, если блокировка не найдена.
     */
    Optional<Blocking> getBlockingByUsers(Long userId, Long blockedUserId);

    /**
     * Возвращает блокировку между пользователями по её указанному идентификатору.
     * @param blockingId идентификатор указанной блокировки.
     * @return Optional, содержащий найденную блокировку, или пустой Optional, если блокировка не найдена.
     */
    Optional<Blocking> getBlockingById(Long blockingId);

    /**
     * Удаляет блокировку между пользователями по их идентификаторам.
     * @param userId идентификатор пользователя инициировавшего блокировку.
     * @param blockedUserId идентификатор заблокированного пользователя.
     * @throws BlockingNotFoundException выбрасывается если блокировка не найдена.
     */
    void deleteBlockingByUsers(Long userId, Long blockedUserId) throws BlockingNotFoundException;

    /**
     * Удаляет блокировку между пользователями по её указанному идентификатору.
     * @param blockingId идентификатор указанной блокировки
     * @throws BlockingNotFoundException выбрасывает если блокировка не найдена.
     */
    void deleteBlockingById(Long blockingId) throws BlockingNotFoundException;
}
