package ru.averkiev.greenchat_user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.greenchat_user.models.ActivityLog;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта ActivityLog с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
}
