package ru.averkiev.greenchat_user.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

/**
 * Класс представляет собой информацию о действиях пользователя, связанных с активностью в системе.
 * @author mrGreenNV
 */
@Entity
@Table(name = "activity_logs")
@Data
@DynamicInsert
public class ActivityLog {

    /**
     * Идентификатор активности.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Пользователь, которому принадлежит данная активность.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * Дата и время проявления активности пользователя.
     */
    @Column(name = "active_at")
    @CreationTimestamp
    private Date activeAt;

    /**
     * Тип активности.
     */
    @Column(name = "activity_type")
    private ActivityType activityType;
}
