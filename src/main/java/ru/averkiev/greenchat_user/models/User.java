package ru.averkiev.greenchat_user.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Класс представляет собой модель пользователя системы.
 * @author mrGreenNV
 */
@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    /**
     * Конструктор для создания пользователя с основными параметрами.
     * @param login имя пользователя.
     * @param password хэшированный пароль пользователя.
     * @param firstname имя.
     * @param lastname фамилия.
     * @param email электронная почта.
     */
    public User(String login,
                String password,
                String firstname,
                String lastname,
                String email) {
        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    /**
     * Идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Имя пользователя для входа в систему - логин.
     */
    @Column(name = "login")
    private String login;

    /**
     * Хэшированный пароль пользователя.
     */
    @Column(name = "password")
    private String password;

    /**
     * Имя пользователя.
     */
    @Column(name = "firstname")
    private String firstname;

    /**
     * Фамилия пользователя.
     */
    @Column(name = "lastname")
    private String lastname;

    /**
     * Email пользователя.
     */
    @Column(name = "email")
    private String email;

    /**
     * Дата и время создания пользователя.
     */
    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * Дата и время обновления данных пользователя.
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * Статус пользователя в системе.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    /**
     * Список ролей пользователя.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    /**
     * Список контактов пользователя.
     */
    @OneToMany(mappedBy = "user")
    private Set<User> contacts;

    /**
     * Список блокировок пользователя, которые он инициировал.
     */
    @OneToMany(mappedBy = "user")
    private Set<Blocking> blockingInitiated;

    /**
     * Список блокировок против данного пользователя.
     */
    @OneToMany(mappedBy = "blockedUser")
    private Set<Blocking> blockingReceived;

    /**
     * Список активностей данного пользователя.
     */
    @OneToMany(mappedBy = "user")
    List<ActivityLog> activityLogs;
}