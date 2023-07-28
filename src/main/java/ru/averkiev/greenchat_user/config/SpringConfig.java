package ru.averkiev.greenchat_user.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author mrGreenNV
 */
@Configuration
@ComponentScan(basePackages = "ru.averkiev.greenchat_user") // Указываем базовый пакет для сканирования компонентов
@EnableJpaRepositories(basePackages = "ru.averkiev.greenchat_user.repositories") // Указываем пакет для сканирования репозиториев
@EnableTransactionManagement // Включаем управление транзакциями
public class SpringConfig {

    /**
     * Создание Bean для преобразования DTO к модели и наоборот.
     * @return объект ModelMapper.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
