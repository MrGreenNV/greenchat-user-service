package ru.averkiev.greenchat_user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

/**
 * Класс представляет собой роли пользователя в системе.
 * @author mrGreenNV
 */
@DynamicInsert
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
    private String roleName;

    /**
     * Дата и время создания роли.
     */
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    /**
     * Дата и время обновления роли.
     */
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    /**
     * Статус роли в системе.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.ACTIVE;

    /**
     * Список пользователей с данной ролью.
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;
}