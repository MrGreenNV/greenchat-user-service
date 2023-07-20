package ru.averkiev.greenchat_user.services;

import ru.averkiev.greenchat_user.exceptions.RegistrationException;
import ru.averkiev.greenchat_user.exceptions.UserNotFoundException;
import ru.averkiev.greenchat_user.models.Blocking;
import ru.averkiev.greenchat_user.models.User;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс описывает функциональность сервиса пользователей.
 * @author mrGreenNV
 */
public interface UserService {
    /**
     * Регистрирует нового пользователя в системе.
     * @param user - новый пользователь.
     * @return - зарегистрированный пользователь.
     * @throws RegistrationException если регистрация пользователя не удалась по каким-либо причинам.
     */
    User register(User user) throws RegistrationException;

    /**
     * Возвращает пользователя по его идентификатору.
     * @param userId - идентификатор искомого пользователя.
     * @return - Optional, содержащий найденного пользователя, или пустой Optional, если пользователь не найден.
     */
    Optional<User> getUserById(Long userId);

    /**
     * Возвращает пользователя по его логину.
     * @param login - логин пользователя.
     * @return - Optional, содержащий найденного пользователя, или пустой Optional, если пользователь не найден.
     */
    Optional<User> getUserByLogin(String login);

    /**
     * Создаёт нового пользователя в системе
     * @param user - новый пользователь.
     * @return - созданный пользователь.
     */
    User createUser(User user);

    /**
     * Обновляет информацию о пользователе с указанным идентификатором.
     * @param userId - идентификатор обновляемого пользователя.
     * @param updateUser - объект, содержащий обновлённые данные пользователя.
     * @return - обновлённый объект пользователя.
     * @throws UserNotFoundException - выбрасывается, если пользователь с указанным идентификатором не найден.
     */
    User updateUser(Long userId, User updateUser) throws UserNotFoundException;

    /**
     * Удаляет пользователя по его идентификатору.
     * @param userId - идентификатор пользователя.
     */
    void deleteUser(Long userId);

    /**
     * Проверяет, существует ли пользователь с указанным идентификатором.
     * @param userId - идентификатор проверяемого пользователя.
     * @return - true, если пользователь существует, иначе false.
     */
    boolean existsUser(Long userId);

    /**
     * Добавляет блокировку между пользователями.
     * @param initiatorUserId - идентификатор инициатора.
     * @param blockedUserId - идентификатор блокируемого пользователя.
     */
    void addBlocking(Long initiatorUserId, Long blockedUserId);

    /**
     * Возвращает список блокировок, инициированных указанным пользователем.
     * @param userId - идентификатор пользователя, инициирующего блокировки.
     * @return - список блокировок.
     */
    List<Blocking> getBlockingInitiatedByUser(Long userId);

    /**
     * Возвращает список блокировок, адресованных указанному пользователю.
     * @param userId - идентификатор указанного пользователя.
     * @return - список блокировок.
     */
    List<Blocking> getBlockingReceivedByUser(Long userId);
}