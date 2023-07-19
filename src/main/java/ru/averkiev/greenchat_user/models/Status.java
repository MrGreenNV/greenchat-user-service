package ru.averkiev.greenchat_user.models;

/**
 * Класс представляющий возможные статусы сущности в системе.
 * @author mrGreenNV
 */
public enum Status {
    /**
     * Сущность активна.
     */
    ACTIVE,
    /**
     * Сущность неактивна.
     */
    NOT_ACTIVE,
    /**
     * Сущность помечена на удаление.
     */
    DELETED
}