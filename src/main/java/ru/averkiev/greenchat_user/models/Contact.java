package ru.averkiev.greenchat_user.models;

import lombok.Data;

import javax.persistence.*;

/**
 * Класс представляет собой контакт пользователя системы.
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
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * Идентификатор пользователя, являющегося контактом.
     */
    @Column(name = "contact_user_id")
    private Long contactUserId;
}