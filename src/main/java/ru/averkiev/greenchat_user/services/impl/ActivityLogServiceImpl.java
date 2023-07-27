package ru.averkiev.greenchat_user.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
     */
    @Override
    public ActivityLog createActivityLog(ActivityLog activityLog) {

        // Проверка, что запись не содержит необходимые данные.
        if (activityLog.getUser() == null || activityLog.getActivityType() == null) {
            log.error("IN createActivityLog - запись не сохранена");
            throw new ActivityLogNotFoundException("В записи недостаточно данных для сохранения");
        }

        activityLog = activityLogRepository.save(activityLog);
        return activityLog;
    }

    /**
     * Возвращает запись об активности по идентификатору.
     * @param activityLogId идентификатор записи об активности.
     * @return Optional, содержащий найденную запись об активности или пустой, если запись не найдена.
     */
    @Override
    public Optional<ActivityLog> getActivityLogById(Long activityLogId) {
        return activityLogRepository.findById(activityLogId);
    }

    /**
     * Возвращает список всех активностей для указанного пользователя.
     * @param user пользователь.
     * @return список всех активностей.
     * @throws UserNotFoundException выбрасывает, если пользователь с указанным идентификатором не найден.
     */
    @Override
    public Optional<List<ActivityLog>> getAllActivityLogsForUser(User user) throws UserNotFoundException {
        return activityLogRepository.findByUser(user);
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
