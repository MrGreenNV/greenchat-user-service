package ru.averkiev.greenchat_user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.greenchat_user.models.Role;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта Role с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}