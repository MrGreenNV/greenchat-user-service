package ru.averkiev.greenchat_user.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.averkiev.greenchat_user.exceptions.BlockingAlreadyExistsException;
import ru.averkiev.greenchat_user.exceptions.BlockingNotFoundException;
import ru.averkiev.greenchat_user.models.Blocking;
import ru.averkiev.greenchat_user.models.User;
import ru.averkiev.greenchat_user.repositories.BlockingRepository;
import ru.averkiev.greenchat_user.services.BlockingService;

import java.util.Optional;

/**
 * Класс реализует функционал для управления блокировками пользователей в системе.
 * @author mrGreenNV
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BlockingServiceImpl implements BlockingService {

    /**
     * Репозиторий для обращения к базе данных.
     */
    private final BlockingRepository blockingRepository;

    /**
     * Создает новую блокировку между пользователями.
     * @param user инициатор блокировки.
     * @param blockedUser блокируемый пользователя.
     * @return возвращает объект новой блокировки.
     * @throws BlockingAlreadyExistsException выбрасывает если такая блокировка уже существует.
     */
    @Override
    public Blocking createBlocking(User user, User blockedUser) throws BlockingAlreadyExistsException {

        // Проверяет существование такой блокировки.
        if (blockingRepository.existsByUserAndBlockedUser(user, blockedUser)) {
            log.error("IN createBlocking - блокировка пользователя не создана");
            throw new BlockingAlreadyExistsException("Блокировка пользователя: " + blockedUser.getLogin() + " уже существует");
        }

        Blocking blocking = new Blocking();
        blocking.setUser(user);
        blocking.setBlockedUser(blockedUser);

        blocking = blockingRepository.save(blocking);
        log.info("IN createBlocking - блокировка успешно создана");
        return blocking;
    }

    /**
     * Возвращает блокировку между пользователями по их идентификаторам.
     * @param user инициатор блокировки.
     * @param blockedUser заблокированный пользователя.
     * @return найденная блокировка
     * @throws BlockingNotFoundException выбрасывает, если блокировка не найдена.
     */
    @Override
    public Blocking getBlockingByUsers(User user, User blockedUser) {
        Optional<Blocking> blocking = blockingRepository.findByUserAndBlockedUser(user, blockedUser);
        if (blocking.isEmpty()) {
            log.error("IN getBlockingByUsers - блокировка между пользователями {} и {} не найдена", user.getLogin(), blockedUser.getLogin());
            throw new BlockingNotFoundException("Блокировка между указанными не найдена");
        }

        log.info("IN getBlockingByUsers - блокировка между пользователями {} и {} успешно найдена", user.getLogin(), blockedUser.getLogin());
        return blocking.get();
    }

    /**
     * Возвращает блокировку между пользователями по её указанному идентификатору.
     * @param blockingId идентификатор указанной блокировки.
     * @return найденная блокировка
     * @throws BlockingNotFoundException выбрасывает, если блокировка не найдена.
     */
    @Override
    public Blocking getBlockingById(Long blockingId) {
        Optional<Blocking> blocking = blockingRepository.findById(blockingId);
        if (blocking.isEmpty()) {
            log.error("IN getBlockingById - блокировка с идентификатором: {} не найдена", blockingId);
            throw new BlockingNotFoundException("Блокировка с указанным идентификатором не найдена");
        }

        log.info("IN getBlockingById - блокировка с идентификатором: {} успешно найдена", blockingId);
        return blocking.get();
    }

    /**
     * Удаляет блокировку между пользователями по их идентификаторам.
     * @param user инициатор блокировки.
     * @param blockedUser заблокированный пользователя.
     * @throws BlockingNotFoundException выбрасывается если блокировка не найдена.
     */
    @Override
    public void deleteBlockingByUsers(User user, User blockedUser) throws BlockingNotFoundException {

        // Проверяет существование такой блокировки.
        if (!blockingRepository.existsByUserAndBlockedUser(user, blockedUser)) {
            log.error("IN deleteBlocking - блокировка пользователя не удалена");
            throw new BlockingNotFoundException("Блокировка пользователя: " + blockedUser.getLogin() + " не найдена");
        }

        blockingRepository.deleteByUserAndBlockedUser(user, blockedUser);
        log.info("IN deleteBlocking - блокировка пользователя успешно удалена");
    }

    /**
     * Удаляет блокировку между пользователями по её указанному идентификатору.
     * @param blockingId идентификатор указанной блокировки
     * @throws BlockingNotFoundException выбрасывает если блокировка не найдена.
     */
    @Override
    public void deleteBlockingById(Long blockingId) throws BlockingNotFoundException {

        // Проверяет существование блокировки с заданным идентификатором.
        if (!blockingRepository.existsBlockingById(blockingId)) {
            log.error("IN deleteBlocking - блокировка пользователя не удалена");
            throw new BlockingNotFoundException("Блокировка с идентификатором: " + blockingId + " не найдена");
        }

        blockingRepository.deleteById(blockingId);
        log.info("IN deleteBlocking - блокировка пользователя успешно удалена");
    }
}