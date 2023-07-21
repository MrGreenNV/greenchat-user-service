package ru.averkiev.greenchat_user.models;

/**
 * @author mrGreenNV
 */
public enum ActivityType {
    /**
     * Вход в систему.
     * Запись активности, когда пользователь выполняет вход в систему, указывая свои учетные данные.
     */
    LOGIN,
    /**
     * Выход из системы.
     * Запись активности, когда пользователь выполняет выход из системы.
     */
    LOGOUT,
    /**
     * Создание аккаунта.
     * Запись активности, когда пользователь регистрирует новый аккаунт в системе.
     */
    ACCOUNT_CREATION,
    /**
     * Изменение профиля.
     * Запись активности, когда пользователь обновляет информацию в своем профиле, такую как имя и т. д.
     */
    PROFILE_UPDATE,
    /**
     * Отправка сообщения.
     * Запись активности, когда пользователь отправляет сообщение другому пользователю.
     */
    MESSAGE_SENT,
    /**
     * Получение сообщения.
     * Запись активности, когда пользователь получает новое сообщение от другого пользователя.
     */
    MESSAGE_RECEIVED,
    /**
     * Удаление сообщения.
     * Запись активности, когда пользователь удаляет сообщение из своей переписки.
     */
    MESSAGE_DELETION,
    /**
     * Создание контакта.
     * Запись активности, когда пользователь добавляет новый контакт в свой список контактов.
     */
    CONTACT_CREATION,
    /**
     * Удаление контакта.
     * Запись активности, когда пользователь удаляет контакт из своего списка контактов.
     */
    CONTACT_DELETION,
    /**
     * Изменение настроек.
     * Запись активности, когда пользователь изменяет свои настройки в системе, такие как настройки конфиденциальности
     * или уведомлений.
     */
    SETTINGS_UPDATE
}