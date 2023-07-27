package ru.averkiev.greenchat_user.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.averkiev.greenchat_user.exceptions.BlockingAlreadyExistsException;
import ru.averkiev.greenchat_user.exceptions.BlockingNotFoundException;
import ru.averkiev.greenchat_user.models.Blocking;
import ru.averkiev.greenchat_user.models.User;
import ru.averkiev.greenchat_user.repositories.BlockingRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Тестовый класс для проверки класса BlockingServiceImpl, реализующего функционал по взаимодействию класса Blocking с
 * @author mrGreenNV
 */
class BlockingServiceImplTest {

    /**
     * Заглушка для тестирования.
     */
    @Mock
    BlockingRepository blockingRepository;

    /**
     * Класс, в который внедряется заглушка.
     */
    @InjectMocks
    BlockingServiceImpl blockingService;

    /**
     * Выполняется перед тестами.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Проверяет вызов метода сохранения в базу данных для создания валидной блокировки.
     */
    @Test
    void testCreateBlocking_ValidBlocking() {

        // Создание тестовых данных.
        Blocking blocking = new Blocking();

        // Установка заглушек.
        when(blockingRepository.existsByUserAndBlockedUser(any(User.class), any(User.class))).thenReturn(false);
        when(blockingRepository.save(any(Blocking.class))).thenReturn(blocking);

        // Вызов тестируемого метода.
        Object result = blockingService.createBlocking(new User(), new User());

        // Проверка результатов.
        verify(blockingRepository,
                times(1)).existsByUserAndBlockedUser(any(User.class), any(User.class));
        verify(blockingRepository,
                times(1)).save(any(Blocking.class));
        assertEquals(Blocking.class, result.getClass());


    }

    /**
     * Проверяет вызов метода сохранения в базу данных для создания дубликата блокировки.
     */
    @Test
    void testCreateBlocking_DuplicateBlocking_TrowBlockingAlreadyExistsException() {

        // Создание тестовых данных.
        User blockedUser = new User();
        blockedUser.setLogin("test_user");

        // Установка заглушек.
        when(blockingRepository.existsByUserAndBlockedUser(any(User.class), any(User.class))).thenReturn(true);

        // Вызов тестируемого метода.
        BlockingAlreadyExistsException result = assertThrows(BlockingAlreadyExistsException.class,
                () -> blockingService.createBlocking(new User(), blockedUser));

        // Проверка результатов.
        assertEquals("Блокировка пользователя: " + blockedUser.getLogin() + " уже существует",
                result.getMessage());
        verify(blockingRepository,
                times(1)).existsByUserAndBlockedUser(any(User.class), any(User.class));
        verify(blockingRepository,
                never()).save(any(Blocking.class));

    }

    /**
     * Проверяет вызовы методов поиска и удаления блокировки по переданным в метод пользователям.
     */
    @Test
    void deleteBlockingByUsers() {

        // Установка заглушек
        when(blockingRepository.existsByUserAndBlockedUser(any(User.class), any(User.class))).thenReturn(true);

        // Вызов тестируемого метода.
        blockingService.deleteBlockingByUsers(new User(), new User());

        // Проверка результатов.
        verify(blockingRepository,
                times(1)).existsByUserAndBlockedUser(any(User.class), any(User.class));
        verify(blockingRepository,
                times(1)).deleteByUserAndBlockedUser(any(User.class), any(User.class));

    }

    /**
     * Проверяет вызовы методов поиска и удаления блокировки, в случае, когда блокировка не найдена по переданным в
     * метод пользователям.
     */
    @Test
    void deleteBlockingByUsers_BlockingNotFound_ThrowsBlockingNotFoundException() {

        // Создание тестовых данных.
        Blocking blocking = new Blocking();
        User blockedUser = new User();
        blockedUser.setLogin("blocked_user");
        blocking.setBlockedUser(blockedUser);

        // Установка заглушек
        when(blockingRepository.existsByUserAndBlockedUser(any(User.class), any(User.class))).thenReturn(false);

        // Вызов тестируемого метода.
        BlockingNotFoundException result = assertThrows(BlockingNotFoundException.class,
                () -> blockingService.deleteBlockingByUsers(new User(), blockedUser));

        // Проверка результатов.
        assertEquals("Блокировка пользователя: " + blockedUser.getLogin() + " не найдена", result.getMessage());
        verify(blockingRepository,
                times(1)).existsByUserAndBlockedUser(any(User.class), any(User.class));
        verify(blockingRepository,
                never()).deleteByUserAndBlockedUser(any(User.class), any(User.class));

    }

    /**
     * Проверяет вызовы методов поиска и удаления блокировки по переданному в метод идентификатору блокировки.
     */
    @Test
    void deleteBlockingById() {
        Long blockingId = 101L;

        // Установка заглушек.
        when(blockingRepository.existsBlockingById(any(Long.class))).thenReturn(true);

        // Вызов тестируемого метода.
        blockingService.deleteBlockingById(blockingId);

        // Проверка результатов.
        verify(blockingRepository, times(1)).existsBlockingById(any(Long.class));
        verify(blockingRepository, times(1)).deleteById(any(Long.class));
    }

    /**
     * Проверяет вызовы методов поиска и удаления блокировки, в случае, когда блокировка не найдена по переданному в
     * метод идентификатору блокировки.
     */
    @Test
    void deleteBlockingById_BlockingNotFound_ThrowsBlockingNotFoundException() {
        Long blockingId = 101L;

        // Установка заглушек.
        when(blockingRepository.existsBlockingById(any(Long.class))).thenReturn(false);

        // Вызов тестируемого метода.
        BlockingNotFoundException result = assertThrows(BlockingNotFoundException.class,
                () -> blockingService.deleteBlockingById(blockingId));

        // Проверка результатов.
        assertEquals("Блокировка с идентификатором: " + blockingId + " не найдена", result.getMessage());
        verify(blockingRepository, times(1)).existsBlockingById(any(Long.class));
        verify(blockingRepository, never()).deleteById(any(Long.class));
    }
}