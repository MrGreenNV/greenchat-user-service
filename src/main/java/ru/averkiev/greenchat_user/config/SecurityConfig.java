package ru.averkiev.greenchat_user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Класс представляет собой настройку безопасности системы
 * @author mrGreenNV
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Позволяет выполнять фильтрацию endpoints, для ограничения аутентификации.
     * @param httpSecurity настройка фильтрации запроса.
     * @return HttpSecurity с назначенными изменениями.
     * @throws Exception выбрасывает, если при внесении изменений были допущены ошибки.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/greenchat/users/register").permitAll()
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

}
