package ru.averkiev.greenchat_user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.greenchat_user.models.ActivityLog;
import ru.averkiev.greenchat_user.models.User;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта ActivityLog с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    /**
     * Получает список активностей по пользователю
     * @param user пользователь, для которого выполняется поиск.
     * @return Optional со списком активностей, если они найдены, иначе пустой.
     */
    Optional<List<ActivityLog>> findByUser(User user);
}
