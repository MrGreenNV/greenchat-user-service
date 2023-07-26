package ru.averkiev.greenchat_user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.greenchat_user.models.Blocking;
import ru.averkiev.greenchat_user.models.User;

import java.util.Optional;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта Blocking с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface BlockingRepository extends JpaRepository<Blocking, Long> {

    /**
     * Проверяет существование блокировки
     * @param user инициатор блокировки.
     * @param blockedUser заблокированный пользователь.
     * @return true если блокировка найдена, иначе false.
     */
    boolean existsByUserAndBlockedUser(User user, User blockedUser);

    /**
     * Проверка существования блокировки по её идентификатору.
     * @param blockingId идентификатор блокировки.
     * @return true, если блокировка найдена иначе false.
     */
    boolean existsBlockingById(Long blockingId);

    /**
     * Выполняет поиск блокировки пользователя в базе данных.
     * @param user пользователь, инициировавший блокировку.
     * @param blockedUser заблокированный пользователь.
     * @return Optional, содержащий блокировку, если она найдена, иначе пустой.
     */
    Optional<Blocking> findByUserAndBlockedUser(User user, User blockedUser);

    /**
     * Удаляет блокировку по пользователям.
     * @param user пользователь, инициировавший блокировку.
     * @param blockedUser заблокированный пользователь.
     */
    void deleteByUserAndBlockedUser(User user, User blockedUser);
}
