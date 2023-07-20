package ru.averkiev.greenchat_user.services;

import ru.averkiev.greenchat_user.exceptions.ContactAlreadyExistsException;
import ru.averkiev.greenchat_user.exceptions.ContactNotFoundException;
import ru.averkiev.greenchat_user.exceptions.UserNotFoundException;
import ru.averkiev.greenchat_user.models.Contact;

import java.util.List;

/**
 * Интерфейс определяет функциональность для управления контактами между пользователями.
 * @author mrGreenNV
 */
public interface ContactService {

    /**
     * Создаёт новый контакт для пользователя с указанным идентификатором.
     * @param userId - идентификатор пользователя.
     * @param contactUserId - идентификатор пользователя, который становится контактом.
     * @return - созданный объект контакта.
     * @throws UserNotFoundException - выбрасывается в случае, если пользователь с указанным идентификатором не найден.
     * @throws ContactAlreadyExistsException - выбрасывается в случае, если контакт уже существует.
     */
    Contact createContact(Long userId, Long contactUserId) throws UserNotFoundException, ContactAlreadyExistsException;

    /**
     * Удаляет контакт по указанному идентификатору.
     * @param contactId - идентификатор контакта.
     * @throws ContactNotFoundException - выбрасывается если контакт не найден.
     */
    void deleteContactById(Long contactId) throws ContactNotFoundException;

    /**
     * Удаляет контакт между пользователями.
     * @param userId идентификатор пользователя, для которого удаляется контакт.
     * @param contactUserId  идентификатор пользователя, контакт с которым удаляется.
     * @throws UserNotFoundException если пользователь с указанным ID не найден.
     * @throws ContactNotFoundException если контакт не найден.
     */
    void deleteContactByUserId(Long userId, Long contactUserId) throws UserNotFoundException, ContactNotFoundException;

    /**
     * Проверяет существование контакта между пользователями.
     * @param userId - идентификатор пользователя, для которого проверяется существование контакта.
     * @param contactUserId - идентификатор пользователя, с которым проверяется контакт.
     * @return true, если контакт существует, иначе - false.
     * @throws UserNotFoundException - выбрасывается, если пользователь с указанным идентификатором не найден.
     */
    boolean existsContact(Long userId, Long contactUserId) throws UserNotFoundException;

    /**
     * Возвращает список контактов для пользователя с указанным идентификатором.
     * @param userId - указанный идентификатор пользователя.
     * @return - список контактов.
     * @throws UserNotFoundException - выбрасывается если пользователь с указанным идентификатором не найден.
     */
    List<Contact> getContactByUserId(Long userId) throws UserNotFoundException;
}
