package ru.averkiev.greenchat_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс для запуска микросервиса.
 */
@SpringBootApplication
public class GreenChatUserServiceApplication {

    /**
     * Запускает проект
     * @param args - аргументы, для запуска приложения.
     */
    public static void main(String[] args) {
        SpringApplication.run(GreenChatUserServiceApplication.class, args);
    }

}