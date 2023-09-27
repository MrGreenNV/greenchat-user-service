package ru.averkiev.greenchat_user.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.averkiev.greenchat_user.exceptions.ActivityLogNotDataException;
import ru.averkiev.greenchat_user.exceptions.ActivityLogNotFoundException;
import ru.averkiev.greenchat_user.exceptions.UserNotFoundException;
import ru.averkiev.greenchat_user.models.ActivityLog;
import ru.averkiev.greenchat_user.models.User;
import ru.averkiev.greenchat_user.repositories.ActivityLogRepository;
import ru.averkiev.greenchat_user.services.ActivityLogService;

import java.util.List;
import java.util.Optional;

/**
 * Класс реализует функционал для управления активностями пользователей в системе.
 * @author mrGreenNV
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    /**
     * Создаёт новую запись об активности пользователя.
     * @param activityLog запись об активности пользователя
     * @return созданная запись об активности.
     * @throws ActivityLogNotDataException, выбрасывает, если для создания записи активности недостаточно данных.
     */
    @Override
    public ActivityLog createActivityLog(ActivityLog activityLog) throws ActivityLogNotDataException {

        // Проверка, что запись содержит необходимые данные.
        if (activityLog.getUser() == null || activityLog.getActivityType() == null) {
            log.error("IN createActivityLog - запись не сохранена");
            throw new ActivityLogNotDataException("В записи недостаточно данных для сохранения");
        }

        log.info("IN createActivityLog - запись успешно сохранена");
        activityLog = activityLogRepository.save(activityLog);
        return activityLog;
    }

    /**
     * Возвращает запись об активности по идентификатору.
     * @param activityLogId идентификатор записи об активности.
     * @return запись об активности пользователя.
     * @exception ActivityLogNotFoundException выбрасывает, если запись об активности не найдена.
     */
    @Override
    public ActivityLog getActivityLogById(Long activityLogId) throws ActivityLogNotFoundException {
        Optional<ActivityLog> activityLog = activityLogRepository.findById(activityLogId);
        if (activityLog.isEmpty()) {
            log.error("IN getActivityLogById - активность пользователя с идентификатором: {} не найдена", activityLogId);
            throw new ActivityLogNotFoundException("Активность пользователя не найдена");
        }

        log.info("IN getActivityLogById - активность пользователя с идентификатором: {} успешно найдена", activityLogId);
        return activityLog.get();
    }

    /**
     * Возвращает список всех активностей для указанного пользователя.
     * @param user пользователь.
     * @return список всех активностей.
     * @throws UserNotFoundException выбрасывает, если пользователь с указанным идентификатором не найден.
     */
    @Override
    public List<ActivityLog> getAllActivityLogsForUser(User user) throws UserNotFoundException {
        Optional<List<ActivityLog>> activityLogs = activityLogRepository.findByUser(user);
        if (activityLogs.isEmpty()) {
            log.error("IN getAllActivityLogsForUser - активности для пользователя: {} не найдены", user.getLogin());
            throw new UserNotFoundException("Активности для пользователя не найдены");
        }

        log.info("IN getAllActivityLogsForUser - активности для пользователя: {} успешно найдены", user.getLogin());
        return activityLogs.get();
    }

    /**
     * Удаляет запись об активности пользователя по её идентификатору.
     * @param activityLogId идентификатор записи об активности
     * @throws ActivityLogNotFoundException выбрасывает если активность не найдена.
     */
    @Override
    public void deleteActivityLog(Long activityLogId) throws ActivityLogNotFoundException {
        if (!activityLogRepository.existsById(activityLogId)) {
            log.error("IN deleteActivityLog - запись не удалена");
            throw new ActivityLogNotFoundException("Запись с id: " + activityLogId + " не найдена");
        }

        activityLogRepository.deleteById(activityLogId);
        log.info("IN deleteActivityLog - запись успешно удалена");
    }
}
