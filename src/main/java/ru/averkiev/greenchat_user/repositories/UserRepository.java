package ru.averkiev.greenchat_user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.greenchat_user.models.User;

import java.util.Optional;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта User с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Выполняет поиск пользователя по логину в базе данных.
     * @param login логин пользователя.
     * @return Optional с пользователем, если он был найден, иначе пустой.
     */
    Optional<User> findByLogin(String login);

    /**
     * Выполняет поиск пользователя по email в базе данных.
     * @param email электронная почта пользователя.
     * @return Optional с пользователем, если он был найден, иначе пустой.
     */
    Optional<User> findByEmail(String email);
}
