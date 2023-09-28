package ru.averkiev.greenchat_user.services;

import ru.averkiev.greenchat_user.exceptions.RoleAlreadyExistsException;
import ru.averkiev.greenchat_user.exceptions.RoleNotFoundException;
import ru.averkiev.greenchat_user.models.Role;
import ru.averkiev.greenchat_user.models.User;

import java.util.List;

/**
 * Интерфейс определяет функциональность для управления ролями пользователей в системе.
 * @author mrGreenNV
 */
public interface RoleService {

    /**
     * Создаёт новую роль с указанным именем.
     * @param roleName имя роли.
     * @return созданный объект роли.
     * @throws RoleAlreadyExistsException - выбрасывается если роль с таким названием уже существует.
     */
    Role createRole(String roleName) throws RoleAlreadyExistsException;

    /**
     * Возвращает объект обновлённой роли.
     * @param roleId идентификатор обновляемой роли.
     * @param roleName новое имя роли.
     * @return объект обновлённой роли
     * @throws RoleNotFoundException - выбрасывается, если роль с указанным идентификатором не найдена.
     */
    Role updateRole(Long roleId, String roleName) throws RoleNotFoundException;

    /**
     * Удаляет роль по указанному идентификатору.
     * @param roleId указанный идентификатор роли.
     * @throws RoleNotFoundException выбрасывается, если роль с таким идентификатором не найдена.
     */
    void deleteRole(Long roleId) throws RoleNotFoundException;

    /**
     * Помечает роль удалённой, но не удаляет физически.
     * @param roleId идентификатор роли.
     * @return роль с измененным статусом.
     * @throws RoleNotFoundException выбрасывается, если роль с таким идентификатором не найдена.
     */
    Role softDeleteRole(Long roleId) throws RoleNotFoundException;

    /**
     * Возвращает роль по указанному идентификатору.
     * @param roleId указанный идентификатор роли.
     * @return найденная роль пользователя.
     * @throws RoleNotFoundException выбрасывается, если роль с таким идентификатором не найдена.
     */
    Role getRoleById(Long roleId) throws RoleNotFoundException;

    /**
     * Возвращает роль по указанному имени.
     * @param roleName указанное имя роли.
     * @return найденная роль пользователя.
     * @throws RoleNotFoundException выбрасывается, если роль с таким идентификатором не найдена.
     */
    Role getRoleByName(String roleName) throws RoleNotFoundException;

    /**
     * Возвращает список всех ролей.
     * @return список ролей.
     */
    List<Role> getAllRoles();

    /**
     * Возвращает список всех пользователь с указанной ролью.
     * @param roleName указанное имя роли.
     * @return список пользователей
     * @throws RoleNotFoundException - выбрасывается, если указанная роль не найдена.
     */
    List<User> getUsersByRole(String roleName) throws RoleNotFoundException;

    /**
     * Проверяет, существует ли роль с данным названием.
     * @param roleName электронная почта проверяемого пользователя.
     * @return true, если пользователь существует, иначе false.
     */
    boolean existsRoleByName(String roleName);
}
