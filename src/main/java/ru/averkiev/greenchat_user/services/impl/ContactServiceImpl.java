package ru.averkiev.greenchat_user.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.averkiev.greenchat_user.exceptions.ContactAlreadyExistsException;
import ru.averkiev.greenchat_user.exceptions.ContactNotFoundException;
import ru.averkiev.greenchat_user.exceptions.UserNotFoundException;
import ru.averkiev.greenchat_user.models.Contact;
import ru.averkiev.greenchat_user.models.User;
import ru.averkiev.greenchat_user.repositories.ContactRepository;
import ru.averkiev.greenchat_user.services.ContactService;

import java.util.List;

/**
 * Класс реализует функционал для управления контактами пользователей в системе.
 * @author mrGreenNV
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    /**
     * Репозиторий для обращения к базе данных.
     */
    private final ContactRepository contactRepository;

    /**
     * Создаёт новый контакт для пользователя с указанным контактом.
     * @param user пользователь, для которого создается контакт.
     * @param contactUser пользователь, контакт с которым создается.
     * @return созданный объект контакта.
     * @throws UserNotFoundException выбрасывает если пользователь не найден.
     * @throws ContactAlreadyExistsException выбрасывает если контакт уже существует.
     */
    @Override
    public Contact createContact(User user, User contactUser) throws ContactAlreadyExistsException {

        // Проверка дублирования контакта.
        if (existsContact(user, contactUser)) {
            log.error("IN createContact - контакт не создан");
            throw  new ContactAlreadyExistsException("Такой контакт уже существует");
        }

        // Создание нового контакта.
        Contact contact = new Contact();
        contact.setUser(user);
        contact.setContactUser(contactUser);

        contact = contactRepository.save(contact);
        log.info("IN createContact - контакт успешно создан");

        return contact;
    }

    /**
     * Удаляет контакт по указанному идентификатору.
     * @param contactId - идентификатор контакта.
     * @throws ContactNotFoundException - выбрасывается если контакт не найден.
     */
    @Override
    public void deleteContactById(Long contactId) throws ContactNotFoundException {

        // Получение контакта из базы данных.
        Contact contact = contactRepository.findById(contactId).orElse(null);

        // Если контакт не найден.
        if (contact == null) {
            log.error("IN deleteContact - контакт не удалён");
            throw new ContactNotFoundException("Контакт с id: " + contactId + " не найден");
        }

        contactRepository.delete(contact);
        log.info("IN deleteContact - контакт успешно удалён");
    }

    /**
     * Удаляет контакт между пользователями.
     * @param user пользователь для которого удаляется контакт.
     * @param contactUser пользователь контакт с которым удаляется.
     * @throws UserNotFoundException если пользователь с указанным ID не найден.
     * @throws ContactNotFoundException если контакт не найден.
     */
    @Override
    public void deleteContactByUser(User user, User contactUser) throws ContactNotFoundException {

        // Получение контакта из базы данных.
        Contact contact = contactRepository.findByUserAndContactUser(user, contactUser).orElse(null);

        // Если контакт не найден.
        if (contact == null) {
            log.error("IN deleteContact - контакт не удалён");
            throw new ContactNotFoundException("Контакт пользователя: " + user.getLogin() + " с пользователем: " + contactUser.getLogin() + " не найден");
        }

        deleteContactById(contact.getId());
        log.info("IN deleteContact - контакт успешно удалён");
    }

    /**
     * Проверяет существование контакта между пользователями.
     * @param user пользователь, для которого проверяется существование контакта.
     * @param contactUser пользователь, с которым проверяется контакт.
     * @return true, если контакт существует, иначе - false.
     * @throws UserNotFoundException выбрасывает если пользователь с указанным идентификатором не найден.
     */
    @Override
    public boolean existsContact(User user, User contactUser) throws UserNotFoundException {
        return contactRepository.existsByUserAndContactUser(user, contactUser);
    }

    /**
     * Возвращает список контактов для пользователя с указанным идентификатором.
     * @param user пользователь, для которого возвращается список контактов.
     * @return список контактов.
     * @throws UserNotFoundException выбрасывает если пользователь с указанным идентификатором не найден.
     */
    @Override
    public List<Contact> getContactByUser(User user) throws UserNotFoundException {
        return user.getContacts();
    }
}