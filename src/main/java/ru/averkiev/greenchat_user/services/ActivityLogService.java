package ru.averkiev.greenchat_user.services;

import ru.averkiev.greenchat_user.exceptions.ActivityLogNotFoundException;
import ru.averkiev.greenchat_user.exceptions.UserNotFoundException;
import ru.averkiev.greenchat_user.models.ActivityLog;
import ru.averkiev.greenchat_user.models.User;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс определяет функциональность для управления записями активностей пользователей.
 * @author mrGreenNV
 */
public interface ActivityLogService {

    /**
     * Создаёт новую запись об активности пользователя.
     * @param activityLog запись об активности пользователя
     * @return созданная запись об активности.
     */
    ActivityLog createActivityLog(ActivityLog activityLog);

    /**
     * Возвращает запись об активности по идентификатору.
     * @param activityLogId идентификатор записи об активности.
     * @return Optional, содержащий найденную запись об активности или пустой, если запись не найдена.
     */
    Optional<ActivityLog> getActivityLogById(Long activityLogId);

    /**
     * Возвращает список всех активностей для указанного пользователя.
     * @param user пользователь.
     * @return список всех активностей.
     * @throws UserNotFoundException выбрасывает, если пользователь с указанным идентификатором не найден.
     */
    Optional<List<ActivityLog>> getAllActivityLogsForUser(User user) throws UserNotFoundException;

    /**
     * Удаляет запись об активности пользователя по её идентификатору.
     * @param activityLogId идентификатор записи об активности
     * @throws ActivityLogNotFoundException выбрасывает если активность не найдена.
     */
    void deleteActivityLog(Long activityLogId) throws ActivityLogNotFoundException;
}
