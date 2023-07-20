package ru.averkiev.greenchat_user.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
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
     * @param login - имя пользователя.
     * @param password - хэшированный пароль пользователя.
     * @param firstname - имя.
     * @param lastname - фамилия.
     * @param email - электронная почта.
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
    @Size(min = 6, max = 254, message = "Логин должен иметь больше 5 и меньше 255 символов")
    @Pattern(regexp = "^[a-zA-Z0-9]*_?[a-zA-Z0-9]*$", message = "В логине допустимы цифры, латинские буквы и один символ '_'")
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
    @Size(min = 3, max = 99, message = "Имя должно иметь больше 2 и меньше 100 символов")
    @Pattern(regexp = "^[a-zA-Z]*$|^[а-яА-Я]*$", message = "В имени допустимы только буквы")
    @Column(name = "firstname")
    private String firstname;

    /**
     * Фамилия пользователя.
     */
    @Size(min = 3, max = 99, message = "Фамилия должна иметь больше 2 и меньше 100 символов")
    @Pattern(regexp = "^[a-zA-Z]*$|^[а-яА-Я]*$", message = "В фамилии допустимы только буквы")
    @Column(name = "lastname")
    private String lastname;

    /**
     * Email пользователя.
     */
    @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+.+.[A-Za-z]{2,4}$", message = "Email должен быть валидным")
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

    @OneToMany(mappedBy = "user")
    private Set<User> contacts;

    @OneToMany(mappedBy = "user")
    private Set<Blocking> blockingInitiated;

    @OneToMany(mappedBy = "blockedUser")
    private Set<Blocking> blockingReceived;
}