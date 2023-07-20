package ru.averkiev.greenchat_user.services;

import ru.averkiev.greenchat_user.exceptions.RoleAlreadyExistsException;
import ru.averkiev.greenchat_user.exceptions.RoleNotFoundException;
import ru.averkiev.greenchat_user.models.Role;
import ru.averkiev.greenchat_user.models.User;

import java.util.List;
import java.util.Optional;

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
     * Возвращает роль по указанному идентификатору.
     * @param roleId указанный идентификатор роли.
     * @return Optional, содержащий найденную роль, или пустой Optional, если роль не найдена.
     */
    Optional<Role> getRoleById(Long roleId);

    /**
     * Возвращает роль по указанному имени.
     * @param roleName указанное имя роли.
     * @return Optional, содержащий найденную роль, или пустой Optional, если роль не найдена.
     */
    Optional<Role> getRoleByName(String roleName);

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
}
