package ru.averkiev.greenchat_user.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

/**
 * Класс представляет собой роли пользователя в системе.
 * @author mrGreenNV
 */
@Entity
@Table(name = "roles")
@Data
public class Role {

    /**
     * Идентификатор роли.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Название роли.
     */
    @Column(name = "name")
    private String name_role;

    /**
     * Дата и время создания роли.
     */
    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * Дата и время обновления роли.
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * Статус роли в системе.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    /**
     * Список пользователей с данной ролью.
     */
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;
}