package ru.averkiev.greenchat_user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.greenchat_user.models.Blocking;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта Blocking с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface BlockingRepository extends JpaRepository<Blocking, Long> {
}
