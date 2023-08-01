package ru.averkiev.greenchat_user.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

/**
 * Класс представляет собой модель пользователя системы.
 * @author mrGreenNV
 */
@Getter
@Setter
@Entity
@DynamicInsert
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
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    /**
     * Дата и время обновления данных пользователя.
     */
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    /**
     * Статус пользователя в системе.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.ACTIVE;

    /**
     * Список ролей пользователя.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    /**
     * Список контактов пользователя.
     */
    @OneToMany(mappedBy = "user")
    private List<Contact> contacts;

    /**
     * Список блокировок пользователя, которые он инициировал.
     */
    @OneToMany(mappedBy = "user")
    private List<Blocking> blockingInitiated;

    /**
     * Список блокировок против данного пользователя.
     */
    @OneToMany(mappedBy = "blockedUser")
    private List<Blocking> blockingReceived;

    /**
     * Список активностей данного пользователя.
     */
    @OneToMany(mappedBy = "user")
    List<ActivityLog> activityLogs;
}