package ru.averkiev.greenchat_user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.greenchat_user.models.User;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта User с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}