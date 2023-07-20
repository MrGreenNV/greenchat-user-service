package ru.averkiev.greenchat_user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.greenchat_user.models.Contact;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта Contact с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
