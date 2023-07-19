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
     * @param username - имя пользователя.
     * @param password - хэшированный пароль пользователя.
     * @param firstname - имя.
     * @param lastname - фамилия.
     * @param email - электронная почта.
     */
    public User(String username,
                String password,
                String firstname,
                String lastname,
                String email) {
        this.username = username;
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
    @Size(min = 6, max = 254, message = "Логин должен быть больше 5 и меньше 255 символов")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "В имени допустимы цифры, латинские буквы и символ '_'")
    @Column(name = "username")
    private String username;

    /**
     * Хэшированный пароль пользователя.
     */
    @Column(name = "password")
    private String password;

    /**
     * Имя пользователя.
     */
    @Size(min = 3, max = 99, message = "Имя должно быть больше двух и меньше 100 символов")
    @Pattern(regexp = "^[a-zA-Z]*$|^[а-яА-Я]*\\$]")
    @Column(name = "firstname")
    private String firstname;

    /**
     * Фамилия пользователя.
     */
    @Size(min = 3, max = 99, message = "Фамилия должна быть больше двух и меньше 100 символов")
    @Pattern(regexp = "^[a-zA-Z]*$|^[а-яА-Я]*\\$]")
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
     * Статус в системе пользователя.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}