package ru.averkiev.greenchat_user.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.averkiev.greenchat_user.exceptions.RoleAlreadyExistsException;
import ru.averkiev.greenchat_user.exceptions.RoleNotFoundException;
import ru.averkiev.greenchat_user.models.Role;
import ru.averkiev.greenchat_user.models.Status;
import ru.averkiev.greenchat_user.repositories.RoleRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Тестовый класс для проверки класса RoleServiceImpl, реализующего функционал по взаимодействию класса Role с базой
 * @author mrGreenNV
 */
class RoleServiceImplTest {

    // объявление объекта заглушки.
    @Mock
    RoleRepository roleRepository;

    // Внедрение заглушек в сервис.
    @InjectMocks
    RoleServiceImpl roleService;

    // Выполнится перед тестами.
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Проверяет создание новой роли с уникальным названием.
     */
    @Test
    void testCreateRole_ValidRoleName_ReturnRole() {

        // Создание тестовых данных.
        String roleName = "test_role";

        Role role = new Role();
        role.setRoleName(roleName);

        // Установка заглушек.
        when(roleRepository.findByRoleName(roleName)).thenReturn(Optional.empty());
        when(roleRepository.save(role)).thenReturn(role);

        // Вызов тестируемого метода.
        roleService.createRole(roleName);

        // Проверка результатов.
        verify(roleRepository, times(1)).findByRoleName(roleName);
        verify(roleRepository, times(1)).save(role);

    }

    /**
     * Проверяет создание новой роли с дублирующим названием.
     */
    @Test
    void testCreateRole_DuplicateRoleName_ThrowRoleAlreadyExistsException() {

        // Создание тестовых данных.
        String roleName = "test_role";

        Role role = new Role();
        role.setRoleName(roleName);

        // Установка заглушек.
        when(roleRepository.findByRoleName(roleName)).thenReturn(Optional.of(role));

        // Вызов тестируемого метода.
        RoleAlreadyExistsException result = assertThrows(RoleAlreadyExistsException.class, () -> roleService.createRole(roleName));

        // Проверка результатов.
        assertEquals("Роль с названием: " + roleName + " уже существует", result.getMessage());
        verify(roleRepository, times(1)).findByRoleName(roleName);
        verify(roleRepository, never()).save(role);

    }

    /**
     * Проверяет обновление роли с валидными данными.
     */
    @Test
    void testUpdateRole_ValidRole_ReturnRole() {

        // Создание тестовых данных.
        Long roleId = 1L;
        String newRoleName = "ROLE_NEW";

        Role savedRole = new Role();
        savedRole.setId(roleId);
        savedRole.setRoleName("ROLE_CURRENT");

        // Установка заглушек.
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(savedRole));
        when(roleRepository.findByRoleName(newRoleName)).thenReturn(Optional.empty());
        when(roleRepository.save(savedRole)).thenReturn(savedRole);

        // Вызов тестируемого метода.
        Role result = roleService.updateRole(roleId, newRoleName);

        // Проверка результатов.
        assertEquals(newRoleName, result.getRoleName());
        verify(roleRepository, times(1)).findById(roleId);
        verify(roleRepository, times(1)).findByRoleName(newRoleName);
        verify(roleRepository, times(1)).save(savedRole);

    }

    /**
     * Проверяет обновление роли с валидными данными.
     */
    @Test
    void testUpdateRole_RoleNotFound_ThrowsRoleNotFoundException() {

        // Создание тестовых данных.
        Long roleId = 1L;
        String newRoleName = "ROLE_NEW";


        // Установка заглушек.
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        // Вызов тестируемого метода.
        RoleNotFoundException result = assertThrows(RoleNotFoundException.class, () -> roleService.updateRole(roleId, newRoleName));

        // Проверка результатов.
        assertEquals("Роль с id: " + roleId + " не найдена", result.getMessage());
        verify(roleRepository, times(1)).findById(roleId);

    }

    /**
     * Проверяет вызов метода, удаляющего роль из базы данных.
     */
    @Test
    void testDeleteRole() {

        // Создание тестовых данных.
        Long roleId = 1L;
        Role role = new Role();
        role.setId(roleId);

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        // Вызов тестируемого метода.
        roleService.deleteRole(role.getId());

        // Проверка результатов.
        verify(roleRepository, times(1)).delete(role);

    }

    /**
     * Проверяет изменение статуса роли при "мягком" удалении роли.
     */
    @Test
    void softDeleteRole() {

        // Создание тестовых данных.
        Long roleId = 1L;
        Role role = new Role();
        role.setId(roleId);
        role.setStatus(Status.ACTIVE);

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        // Вызов тестируемого метода.
        roleService.softDeleteRole(roleId);

        // Проверка результатов.
        assertEquals(Status.DELETED, role.getStatus());
        verify(roleRepository, never()).delete(role);
    }
}