package ru.averkiev.greenchat_user.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.averkiev.greenchat_user.utils.JwtFilter;

/**
 * Класс представляет собой настройку безопасности системы
 * @author mrGreenNV
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Фильтр запросов
     */
    private final JwtFilter jwtFilter;

    /**
     * Позволяет выполнять фильтрацию endpoints, для ограничения аутентификации.
     * @param httpSecurity настройка фильтрации запроса.
     * @return HttpSecurity с назначенными изменениями.
     * @throws Exception выбрасывает, если при внесении изменений были допущены ошибки.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(
                                "/greenchat/users/register",
                                "greenchat/users/{username}",
                                "/swagger-ui/**",
                                "/swagger.yaml",
                                "/v3/api-docs/**",
                                "/swagger-resources/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    /**
     * Позволяет создать bean BCryptPasswordEncoder.
     * @return новый объект BCryptPasswordEncoder.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
