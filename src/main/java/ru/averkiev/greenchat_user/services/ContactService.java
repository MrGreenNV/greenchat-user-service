package ru.averkiev.greenchat_user.services;

import ru.averkiev.greenchat_user.exceptions.ContactAlreadyExistsException;
import ru.averkiev.greenchat_user.exceptions.ContactNotFoundException;
import ru.averkiev.greenchat_user.exceptions.UserNotFoundException;
import ru.averkiev.greenchat_user.models.Contact;
import ru.averkiev.greenchat_user.models.User;

import java.util.List;

/**
 * Интерфейс определяет функциональность для управления контактами между пользователями.
 * @author mrGreenNV
 */
public interface ContactService {

    /**
     * Создаёт новый контакт для пользователя с указанным контактом.
     * @param user пользователь, для которого создается контакт.
     * @param contactUser пользователь, контакт с которым создается.
     * @return созданный объект контакта.
     * @throws UserNotFoundException выбрасывает если пользователь не найден.
     * @throws ContactAlreadyExistsException выбрасывает если контакт уже существует.
     */
    Contact createContact(User user, User contactUser) throws ContactAlreadyExistsException;

    /**
     * Удаляет контакт по указанному идентификатору.
     * @param contactId - идентификатор контакта.
     * @throws ContactNotFoundException - выбрасывается если контакт не найден.
     */
    void deleteContactById(Long contactId) throws ContactNotFoundException;

    /**
     * Удаляет контакт между пользователями.
     * @param user пользователь для которого удаляется контакт.
     * @param contactUser пользователь контакт с которым удаляется.
     * @throws UserNotFoundException если пользователь с указанным ID не найден.
     * @throws ContactNotFoundException если контакт не найден.
     */
    void deleteContactByUser(User user, User contactUser) throws UserNotFoundException, ContactNotFoundException;

    /**
     * Проверяет существование контакта между пользователями.
     * @param user пользователь, для которого проверяется существование контакта.
     * @param contactUser пользователь, с которым проверяется контакт.
     * @return true, если контакт существует, иначе - false.
     * @throws UserNotFoundException выбрасывает если пользователь с указанным идентификатором не найден.
     */
    boolean existsContact(User user, User contactUser) throws UserNotFoundException;

    /**
     * Возвращает список контактов для пользователя с указанным идентификатором.
     * @param user пользователь, для которого возвращается список контактов.
     * @return список контактов.
     * @throws UserNotFoundException выбрасывает если пользователь с указанным идентификатором не найден.
     */
    List<Contact> getContactByUser(User user) throws UserNotFoundException;
}
