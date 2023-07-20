package ru.averkiev.greenchat_user.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * Класс представляет собой информацию о действиях пользователя, связанных с активностью в системе.
 * @author mrGreenNV
 */
@Entity
@Table(name = "activity_logs")
@Data
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
    @CreatedDate
    @Column(name = "active_at")
    private Date activeAt;

    /**
     * Тип активности.
     */
    @Column(name = "activity_type")
    private ActivityType activityType;
}
