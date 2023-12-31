package ru.averkiev.greenchat_user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.greenchat_user.models.Role;

import java.util.Optional;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта Role с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Выполняет поиск роли в базе данных по её названию.
     * @param roleName название роли.
     * @return Optional с ролью, если она была найдена, иначе пустой.
     */
    Optional<Role> findByRoleName(String roleName);
}
