package ru.averkiev.greenchat_user.models;

import lombok.Data;

import javax.persistence.*;

/**
 * Класс представляет собой информацию о блокировках между пользователями. Блокировка позволяет пользователю
 * ограничить доступ к своему профилю, сообщениям и другим взаимодействиям со стороны других пользователей.
 * @author mrGreenNV
 */
@Entity
@Table(name = "block_list")
@Data
public class Blocking {

    /**
     * Идентификатор контакта.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Пользователь, инициировавший блокировку
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * Пользователь, подвергнувшийся блокировке.
     */
    @ManyToOne
    @JoinColumn(name = "blocked_user_id", referencedColumnName = "id")
    private User blockedUser;
}
