package ru.averkiev.greenchat_user.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.averkiev.greenchat_user.exceptions.*;
import ru.averkiev.greenchat_user.models.Blocking;
import ru.averkiev.greenchat_user.models.Role;
import ru.averkiev.greenchat_user.models.Status;
import ru.averkiev.greenchat_user.models.User;
import ru.averkiev.greenchat_user.models.dto.user.*;
import ru.averkiev.greenchat_user.repositories.RoleRepository;
import ru.averkiev.greenchat_user.repositories.UserRepository;
import ru.averkiev.greenchat_user.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс реализует функционал взаимодействия User с базой данных.
 * @author mrGreenNV
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /** Сервис для взаимодействия с хэшированными паролями. */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * ModelMapper для преобразования объектов DTO и объектов модели.
     */
    private final ModelMapper modelMapper;

    /**
     * Репозиторий для обращения к базе данных.
     */
    private final UserRepository userRepository;

    /**
     * Репозиторий для обращения к базе данных.
     */
    private final RoleRepository roleRepository;

    /**
     * Регистрирует нового пользователя в системе.
     * @param userCreateDTO DTO данные нового пользователя.
     * @return зарегистрированный пользователь.
     * @throws RegistrationException выбрасывает если регистрация пользователя не удалась по каким-либо причинам.
     */
    @Override
    public UserRegistrationDTO register(UserCreateDTO userCreateDTO) throws RegistrationException {

        // Получение объекта User из DTO.
        User user = modelMapper.map(userCreateDTO, User.class);

        try {

            // Проверка на дублирование логина.
            if (existsUserByLogin(user.getLogin())) {
                log.error("IN register - пользователь с именем: '{}' не был зарегистрирован", userCreateDTO.getLogin());
                throw new UserWithLoginAlreadyExistsException("Данный логин уже зарегистрирован");
            }

            // Проверка на дублирование email.
            if (existsUserByEmail(user.getEmail())) {
                throw new UserWithEmailAlreadyExistsException("Данный email уже зарегистрирован");
            }

            // Проверка паролей.
            if (!userCreateDTO.getPassword().equals(userCreateDTO.getConfirmPassword())) {
                log.error("IN register - пользователь с именем: '{}' не был зарегистрирован", userCreateDTO.getLogin());
                throw new PasswordsNotMatchException("Пароли не совпадают");
            }

        } catch (PasswordsNotMatchException | UserWithLoginAlreadyExistsException | UserWithEmailAlreadyExistsException regEx) {

            log.error("IN register - пользователь с логином '{}' не был зарегистрирован", user.getLogin(), regEx);
            throw new RegistrationException(regEx.getMessage(), regEx);

        }

        // Хэширование пароля для безопасности хранения в базе данных.
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // Назначение пользователю роли по умолчанию.
        user.setRoles(new ArrayList<>());
        user.getRoles().add(roleRepository.findByRoleName("ROLE_USER").orElseThrow(() -> new RoleNotFoundException("Роль не найдена")));

        // Вызов метода сохранения пользователя.
        user = saveUser(user);

        log.info("IN register - пользователь с логином '{}' успешно зарегистрирован", user.getLogin());

        return modelMapper.map(user, UserRegistrationDTO.class);
    }

    /**
     * Создаёт нового пользователя в системе
     * @param user новый пользователь.
     * @return созданный пользователь.
     */
    @Override
    public User saveUser(User user) {
        user = userRepository.save(user);
        log.info("IN saveUser - пользователь с логином '{}' успешно сохранен", user.getLogin());
        return user;
    }

    /**
     * Возвращает пользователя по его идентификатору.
     * @param userId идентификатор искомого пользователя.
     * @return Optional, содержащий найденного пользователя, или пустой Optional, если пользователь не найден.
     */
    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Возвращает пользователя по его логину.
     * @param login логин пользователя.
     * @return Optional, содержащий найденного пользователя, или пустой Optional, если пользователь не найден.
     */
    @Override
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    /**
     * Обновляет информацию о пользователе с указанным идентификатором.
     * @param userId идентификатор обновляемого пользователя.
     * @param userUpdateDTO объект, содержащий обновлённые данные пользователя.
     * @return обновлённый объект пользователя.
     * @throws UserNotFoundException выбрасывает если пользователь с указанным идентификатором не найден.
     */
    @Override
    public UserUpdateDTO updateUser(Long userId, UserUpdateDTO userUpdateDTO) throws UserNotFoundException {

        // Поиск обновляемого пользователя. Если такого нет - возвращает null;
        User user = getUserById(userId).orElse(null);

        // Обработка поиска в случае, если пользователь не найден.
        if (user == null) {
            log.error("IN updateUser - пользователь с id {} не найден.", userId);
            throw new UserNotFoundException("Пользователь с id: " + userId + " не найден");
        }

        // Получение объекта User из DTO.
        User updateUser = modelMapper.map(userUpdateDTO, User.class);

        // Проверка логина.
        if (updateUser.getLogin() != null && !updateUser.getLogin().equals(user.getLogin())) {
            if (!existsUserByLogin(updateUser.getLogin())) {
                user.setLogin(updateUser.getLogin());
            }
        }

        // Проверки имени.
        if (updateUser.getFirstname() != null && !updateUser.getFirstname().equals(user.getFirstname())) {
                user.setFirstname(updateUser.getFirstname());
        }

        // Проверка фамилии.
        if (updateUser.getLastname() != null && !updateUser.getLastname().equals(user.getLastname())) {
            user.setLastname(updateUser.getLastname());
        }

        // Проверка email.
        if (updateUser.getEmail() != null && !updateUser.getEmail().equals(user.getEmail())) {
            if (!existsUserByLogin(updateUser.getEmail())) {
                user.setEmail(updateUser.getEmail());
            }
        }

        // Сохранение обновленного пользователя.
        user = userRepository.save(user);

        log.info("IN updateUser - пользователь успешно обновлён");

        return modelMapper.map(user, UserUpdateDTO.class);
    }

    /**
     * Обновляет пароль пользователя с указанным идентификатором.
     * @param userId указанный идентификатор пользователя.
     * @param userUpdatePasswordDTO объект содержащий новый пароль.
     * @throws UserNotFoundException выбрасывает если пользователь с указанным идентификатором не найден.
     */
    @Override
    public void updateUserPassword(Long userId, UserUpdatePasswordDTO userUpdatePasswordDTO) throws UserNotFoundException, PasswordsNotMatchException {

        // Поиск обновляемого пользователя. Если такого нет - возвращает null;
        User user = getUserById(userId).orElse(null);

        // Обработка поиска в случае, если пользователь не найден.
        if (user == null) {
            log.error("IN updatePassword - пароль не обновлён");
            throw new UserNotFoundException("Пользователь с id: " + userId + " не найден");
        }

        // Проверка на корректность текущего пароля.
        if (!userUpdatePasswordDTO.getCurrentPassword().equals(user.getPassword())) {
            log.error("IN updatePassword - пароль не обновлён");
            throw new PasswordsNotMatchException("Неправильный текущий пароль");
        }

        // Проверка совпадения нового пароля и его подтверждения.
        if (!userUpdatePasswordDTO.getNewPassword().equals(userUpdatePasswordDTO.getConfirmPassword())) {
            log.error("IN updatePassword - пароль не обновлён");
            throw new PasswordsNotMatchException("Пароль и подтверждение пароля не совпадают");
        }

        // Проверка совпадения старого и нового пароля.
        if (userUpdatePasswordDTO.getNewPassword().equals(user.getPassword())) {
            log.error("IN updatePassword - пароль не обновлён");
            throw new PasswordsNotMatchException("Новый пароль должен отличаться от текущего");
        }

        user.setPassword(userUpdatePasswordDTO.getNewPassword());

        // Сохранение пользователя с обновленным паролем.
        userRepository.save(user);
        log.info("IN updatePassword - пароль успешно обновлён");
    }

    /**
     * Удаляет пользователя по его идентификатору.
     * @param userId идентификатор удаляемого пользователя.
     */
    @Override
    public void deleteUser(Long userId) {

        // Поиск удаляемого пользователя. Если такого нет - возвращает null;
        User user = getUserById(userId).orElse(null);

        // Обработка поиска в случае, если пользователь не найден.
        if (user == null) {
            log.error("IN updateUser - пользователь с id {} не удалён. ", userId);
            throw new UserNotFoundException("Пользователь с id: " + userId + " не найден");
        }

        userRepository.delete(user);
        log.info("IN deleteUser - пользователь полностью удален");
    }

    /**
     * Помечает пользователя удалённым, но не удаляет физически.
     * @param userId идентификатор пользователя.
     */
    public UserStatusDTO softDeleteUser(Long userId) {

        // Поиск удаляемого пользователя. Если такого нет - возвращает null;
        User user = getUserById(userId).orElse(null);

        // Обработка поиска в случае, если пользователь не найден.
        if (user == null) {
            log.error("IN updateUser - пользователь с id {} не найден.", userId);
            throw new UserNotFoundException("Пользователь с id: " + userId + " не найден");
        }

        user.setStatus(Status.DELETED);
        userRepository.save(user);
        log.info("IN softDeleteUser - пользователь успешно помечен под удаление");

        return modelMapper.map(user, UserStatusDTO.class);
    }

    /**
     * Возвращает список всех пользователей.
     * @return список ролей.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Возвращает список всех ролей по имени пользователя.
     * @param login указанное имя пользователя.
     * @return список пользователей
     * @throws RoleNotFoundException - выбрасывается, если указанная роль не найдена.
     */
    public List<Role> getRolesByLogin(String login) throws UserNotFoundException {

        // Поиск пользователя по имени.
        User user = getUserByLogin(login).orElse(null);

        // Случай, когда пользователь не найден.
        if (user == null) {
            log.error("IN getRolesByLogin - поиск ролей завершён с ошибкой");
            throw new UserNotFoundException("Пользователь с логином: " + login + " не найден");
        }

        log.info("IN getRolesByLogin - поиск ролей успешно завершён");
        return user.getRoles();

    }

    @Override
    public List<Blocking> getBlockingInitiatedByUser(Long userId) {
        return null;
    }

    @Override
    public List<Blocking> getBlockingReceivedByUser(Long userId) {
        return null;
    }

    /**
     * Проверяет, существует ли пользователь с указанным идентификатором.
     * @param userId идентификатор проверяемого пользователя.
     * @return true, если пользователь существует, иначе false.
     */
    @Override
    public boolean existsUserById(Long userId) {
        return userRepository.findById(userId).isPresent();
    }

    /**
     * Проверяет, существует ли пользователь с указанным логином.
     * @param login логин проверяемого пользователя.
     * @return true, если пользователь существует, иначе false.
     */
    @Override
    public boolean existsUserByLogin(String login) {
        return userRepository.findByLogin(login).isPresent();
    }

    /**
     * Проверяет, существует ли пользователь с указанной электронной почтой.
     * @param email электронная почта проверяемого пользователя.
     * @return true, если пользователь существует, иначе false.
     */
    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}