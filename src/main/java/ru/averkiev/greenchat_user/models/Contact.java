package ru.averkiev.greenchat_user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Класс представляет собой контакт пользователя системы.
 * Этот класс отвечает за хранение информации о связях между пользователями.
 * @author mrGreenNV
 */
@Entity
@Table(name = "contacts")
@Data
public class Contact {

    /**
     * Идентификатор контакта.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Владелец данного контакта.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * Пользователь, являющийся контактом.
     */
    @ManyToOne
    @JoinColumn(name = "contact_user_id", referencedColumnName = "id")
    private User contactUser;
}