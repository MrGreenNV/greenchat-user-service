package ru.averkiev.greenchat_user.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.averkiev.greenchat_user.exceptions.ContactAlreadyExistsException;
import ru.averkiev.greenchat_user.models.Contact;
import ru.averkiev.greenchat_user.models.User;
import ru.averkiev.greenchat_user.repositories.ContactRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Тестовый класс для проверки класса ContactServiceImpl, реализующего функционал по взаимодействию класса Contact с
 * базой данных.
 * @author mrGreenNV
 */
class ContactServiceImplTest {

    /**
     * Заглушка для тестирования.
     */
    @Mock
    ContactRepository contactRepository;

    /**
     * Класс, в который внедряется заглушка.
     */
    @InjectMocks
    ContactServiceImpl contactService;

    /**
     * Выполняется перед тестами.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Проверяет создание нового контакта.
     */
    @Test
    void testCreateContact_ValidContact_ReturnContact() {

        // Создание тестовых данных.
        Contact contact = new Contact();

        // Установка заглушек.
        when(contactRepository.existsByUserAndContactUser(any(User.class), any(User.class))).thenReturn(false);
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        // Вызов тестируемого метода.
        contactService.createContact(new User(), new User());

        // Проверка результатов.
        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    /**
     * Проверяет создание нового контакта, дублирующего существующий.
     */
    @Test
    void testCreateContact_DuplicateContact_ThrowsContactAlreadyExistsException() {

        // Установка заглушек.
        when(contactRepository.existsByUserAndContactUser(any(User.class), any(User.class))).thenReturn(true);

        // Вызов тестируемого метода.
        ContactAlreadyExistsException result = assertThrows(ContactAlreadyExistsException.class,
                () -> contactService.createContact(new User(), new User()));

        // Проверка результатов.
        assertEquals("Такой контакт уже существует", result.getMessage());
        verify(contactRepository, never()).save(any(Contact.class));
    }

    /**
     * Проверяет удаление контакта по указанному идентификатору контакта.
     */
    @Test
    void testDeleteContactById_ContactFound() {

        // Создание тестовых данных;
        Long contactId = 1L;
        Contact contact = new Contact();
        contact.setId(contactId);

        // Установка заглушек.
        when(contactRepository.findById(contactId)).thenReturn(Optional.of(contact));

        // Вызов тестируемого метода.
        contactService.deleteContactById(contactId);

        // Проверка результатов.
        verify(contactRepository, times(1)).delete(contact);

    }

    /**
     * Проверяет удаление контакта по указанным пользователям.
     */
    @Test
    void testDeleteContactByUser() {

        // Создание тестовых данных.
        Long contactId = 1L;
        Contact contact = new Contact();
        contact.setId(contactId);

        // Установка заглушек.
        when(contactRepository.findByUserAndContactUser(any(User.class), any(User.class))).thenReturn(Optional.of(contact));
        when(contactRepository.findById(contactId)).thenReturn(Optional.of(contact));

        // Вызов тестируемого метода.
        contactService.deleteContactByUser(new User(), new User());

        // Проверка результатов.
        verify(contactRepository, times(1)).delete(any(Contact.class));
    }
}