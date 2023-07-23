package ru.averkiev.greenchat_user.services;

import ru.averkiev.greenchat_user.exceptions.PasswordsNotMatchException;
import ru.averkiev.greenchat_user.exceptions.RegistrationException;
import ru.averkiev.greenchat_user.exceptions.UserNotFoundException;
import ru.averkiev.greenchat_user.models.Blocking;
import ru.averkiev.greenchat_user.models.User;
import ru.averkiev.greenchat_user.models.dto.user.UpdatePasswordDTO;
import ru.averkiev.greenchat_user.models.dto.user.UserCreateDTO;
import ru.averkiev.greenchat_user.models.dto.user.UserRegistrationDTO;
import ru.averkiev.greenchat_user.models.dto.user.UserUpdateDTO;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс определяет функциональность для управления пользователями.
 * @author mrGreenNV
 */
public interface UserService {
    /**
     * Регистрирует нового пользователя в системе.
     * @param userCreateDTO DTO данные нового пользователя.
     * @return зарегистрированный пользователь.
     * @throws RegistrationException выбрасывает если регистрация пользователя не удалась по каким-либо причинам.
     */
    UserRegistrationDTO register(UserCreateDTO userCreateDTO) throws RegistrationException;

    /**
     * Создаёт нового пользователя в системе
     * @param user новый пользователь.
     * @return созданный пользователь.
     */
    User saveUser(User user);

    /**
     * Возвращает пользователя по его идентификатору.
     * @param userId идентификатор искомого пользователя.
     * @return Optional, содержащий найденного пользователя, или пустой Optional, если пользователь не найден.
     */
    Optional<User> getUserById(Long userId);

    /**
     * Возвращает пользователя по его логину.
     * @param login логин пользователя.
     * @return Optional, содержащий найденного пользователя, или пустой Optional, если пользователь не найден.
     */
    Optional<User> getUserByLogin(String login);

    /**
     * Обновляет информацию о пользователе с указанным идентификатором.
     * @param userId идентификатор обновляемого пользователя.
     * @param userUpdateDTO объект, содержащий обновлённые данные пользователя.
     * @return обновлённый объект пользователя.
     * @throws UserNotFoundException выбрасывает если пользователь с указанным идентификатором не найден.
     */
    UserUpdateDTO updateUser(Long userId, UserUpdateDTO userUpdateDTO) throws UserNotFoundException;

    /**
     * Обновляет пароль пользователя с указанным идентификатором.
     * @param userId указанный идентификатор пользователя.
     * @param updatePasswordDTO объект содержащий новый пароль.
     * @throws UserNotFoundException выбрасывает если пользователь с указанным идентификатором не найден.
     */
    void updateUserPassword(Long userId, UpdatePasswordDTO updatePasswordDTO) throws UserNotFoundException, PasswordsNotMatchException;

    /**
     * Удаляет пользователя по его идентификатору.
     * @param userId идентификатор удаляемого пользователя.
     */
    void deleteUser(Long userId);

    /**
     * Помечает пользователя удалённым, но не удаляет физически.
     * @param userId идентификатор пользователя.
     */
    void softDeleteUser(Long userId);

    /**
     * Возвращает список блокировок, инициированных указанным пользователем.
     * @param userId идентификатор пользователя, инициирующего блокировки.
     * @return список блокировок.
     */
    List<Blocking> getBlockingInitiatedByUser(Long userId);

    /**
     * Возвращает список блокировок, адресованных указанному пользователю.
     * @param userId идентификатор указанного пользователя.
     * @return список блокировок.
     */
    List<Blocking> getBlockingReceivedByUser(Long userId);

    /**
     * Проверяет, существует ли пользователь с указанным идентификатором.
     * @param userId идентификатор проверяемого пользователя.
     * @return true, если пользователь существует, иначе false.
     */
    boolean existsUserById(Long userId);

    /**
     * Проверяет, существует ли пользователь с указанным логином.
     * @param login логин проверяемого пользователя.
     * @return true, если пользователь существует, иначе false.
     */
    boolean existsUserByLogin(String login);

    /**
     * Проверяет, существует ли пользователь с указанной электронной почтой.
     * @param email электронная почта проверяемого пользователя.
     * @return true, если пользователь существует, иначе false.
     */
    boolean existsUserByEmail(String email);

}