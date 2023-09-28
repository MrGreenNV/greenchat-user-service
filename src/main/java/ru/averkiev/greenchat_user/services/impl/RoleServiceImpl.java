package ru.averkiev.greenchat_user.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.averkiev.greenchat_user.exceptions.RoleAlreadyExistsException;
import ru.averkiev.greenchat_user.exceptions.RoleNotFoundException;
import ru.averkiev.greenchat_user.models.Role;
import ru.averkiev.greenchat_user.models.Status;
import ru.averkiev.greenchat_user.models.User;
import ru.averkiev.greenchat_user.repositories.RoleRepository;
import ru.averkiev.greenchat_user.services.RoleService;

import java.util.List;
import java.util.Optional;

/**
 * Класс реализует функционал для управления ролями пользователей в системе.
 * @author mrGreenNV
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    /**
     * Репозиторий для обращения к базе данных.
     */
    private  final RoleRepository roleRepository;

    /**
     * Создаёт новую роль с указанным именем.
     * @param roleName имя роли.
     * @return созданный объект роли.
     * @throws RoleAlreadyExistsException - выбрасывается если роль с таким названием уже существует.
     */
    @Override
    public Role createRole(String roleName) throws RoleAlreadyExistsException {

        // Проверка на дублирования названия роли.
        if (existsRoleByName(roleName)) {
            log.error("IN createRole - роль не создана");
            throw new RoleAlreadyExistsException("Роль с названием: " + roleName + " уже существует");
        }

        // Создание объекта Role.
        Role role = new Role();
        role.setRoleName(roleName);

        // Сохранение объекта Role в базу данных.
        role = roleRepository.save(role);
        log.info("IN createRole - роль успешно сохранена");

        return role;
    }

    /**
     * Обновляет информацию о роли.
     * @param roleId идентификатор обновляемой роли.
     * @param roleName новое имя роли.
     * @return объект обновлённой роли
     * @throws RoleNotFoundException - выбрасывается, если роль с указанным идентификатором не найдена.
     */
    @Override
    public Role updateRole(Long roleId, String roleName) throws RoleNotFoundException {

        // Получение роли по идентификатору из базы данных.
        Role role = getRoleById(roleId);

        // Проверка на пустое значение и совпадение с уже существующими ролями.
        if (roleName != null && !existsRoleByName(roleName)) {

            role.setRoleName(roleName);
            role = roleRepository.save(role);

            log.info("IN updateRole - роль успешно обновлена.");
            return role;
        }

        log.error("IN updateRole - роль не обновлена, данные актуальны.");
        return role;
    }

    /**
     * Удаляет роль по указанному идентификатору.
     * @param roleId указанный идентификатор роли.
     * @throws RoleNotFoundException выбрасывается, если роль с таким идентификатором не найдена.
     */
    @Override
    public void deleteRole(Long roleId) throws RoleNotFoundException {

        // Получение роли по идентификатору из базы данных.
        Role role = getRoleById(roleId);

        roleRepository.delete(role);
        log.info("IN deleteRole - роль успешно удалена");

    }

    /**
     * Помечает роль удалённой, но не удаляет физически.
     * @param roleId идентификатор роли.
     * @return роль с измененным статусом.
     * @throws RoleNotFoundException выбрасывается, если роль с таким идентификатором не найдена.
     */
    @Override
    public Role softDeleteRole(Long roleId) throws RoleNotFoundException {

        // Получение роли по идентификатору из базы данных.
        Role role = getRoleById(roleId);

        role.setStatus(Status.DELETED);
        role = roleRepository.save(role);
        log.info("IN softDeleteRole - роль успешно помечена удалённой.");

        return role;
    }

    /**
     * Возвращает роль по указанному идентификатору.
     * @param roleId указанный идентификатор роли.
     * @return найденная роль пользователя.
     * @throws RoleNotFoundException выбрасывается, если роль с таким идентификатором не найдена.
     */
    @Override
    public Role getRoleById(Long roleId) throws RoleNotFoundException {

        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isEmpty()) {
            log.error("IN getRoleByID - роль с идентификатором: {} не найдена", roleId);
            throw new RoleNotFoundException("Роль не найдена");
        }

        log.info("IN getRoleById - роль с идентификатором: {} успешно найдена", roleId);
        return role.get();
    }

    /**
     * Возвращает роль по указанному имени.
     * @param roleName указанное имя роли.
     * @return найденная роль пользователя.
     * @throws RoleNotFoundException выбрасывается, если роль с таким идентификатором не найдена.
     */
    @Override
    public Role getRoleByName(String roleName) throws RoleNotFoundException {

        Optional<Role> role = roleRepository.findByRoleName(roleName);
        if (role.isEmpty()) {
            log.error("IN getRoleByName - роль с наименованием: {} не найдена", roleName);
            throw new RoleNotFoundException("Роль не найдена");
        }

        log.info("IN getRoleByName - роль с наименованием: {} успешно найдена", roleName);
        return role.get();
    }

    /**
     * Возвращает список всех ролей.
     * @return список ролей.
     */
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    /**
     * Возвращает список всех пользователь с указанной ролью.
     * @param roleName указанное имя роли.
     * @return список пользователей
     * @throws RoleNotFoundException - выбрасывается, если указанная роль не найдена.
     */
    @Override
    public List<User> getUsersByRole(String roleName) throws RoleNotFoundException {

        // Поиск роли по имени.
        Role role = getRoleByName(roleName);

        log.info("IN getUserRoles - поиск пользователей успешно завершён");
        return role.getUsers();
    }

    /**
     * Проверяет, существует ли роль с данным названием.
     * @param roleName электронная почта проверяемого пользователя.
     * @return true, если пользователь существует, иначе false.
     */
    @Override
    public boolean existsRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName).isPresent();
    }
}
