package ru.averkiev.greenchat_user.services;

import ru.averkiev.greenchat_user.exceptions.ActivityLogNotDataException;
import ru.averkiev.greenchat_user.exceptions.ActivityLogNotFoundException;
import ru.averkiev.greenchat_user.exceptions.UserNotFoundException;
import ru.averkiev.greenchat_user.models.ActivityLog;
import ru.averkiev.greenchat_user.models.User;

import java.util.List;

/**
 * Интерфейс определяет функциональность для управления записями активностей пользователей.
 * @author mrGreenNV
 */
public interface ActivityLogService {

    /**
     * Создаёт новую запись об активности пользователя.
     * @param activityLog запись об активности пользователя
     * @return созданная запись об активности.
     * @throws ActivityLogNotDataException, выбрасывает, если для создания записи активности недостаточно данных.
     */
    ActivityLog createActivityLog(ActivityLog activityLog);

    /**
     * Возвращает запись об активности по идентификатору.
     * @param activityLogId идентификатор записи об активности.
     * @return запись об активности пользователя.
     * @exception ActivityLogNotFoundException выбрасывает, если запись об активности не найдена.
     */
    ActivityLog getActivityLogById(Long activityLogId) throws ActivityLogNotFoundException;

    /**
     * Возвращает список всех активностей для указанного пользователя.
     * @param user пользователь.
     * @return список всех активностей.
     * @throws UserNotFoundException выбрасывает, если пользователь с указанным идентификатором не найден.
     */
    List<ActivityLog> getAllActivityLogsForUser(User user) throws UserNotFoundException;

    /**
     * Удаляет запись об активности пользователя по её идентификатору.
     * @param activityLogId идентификатор записи об активности
     * @throws ActivityLogNotFoundException выбрасывает если активность не найдена.
     */
    void deleteActivityLog(Long activityLogId) throws ActivityLogNotFoundException;
}
