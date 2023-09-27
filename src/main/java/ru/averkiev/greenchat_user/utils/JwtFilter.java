package ru.averkiev.greenchat_user.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;
import ru.averkiev.greenchat_user.exceptions.AuthException;
import ru.averkiev.greenchat_user.models.dto.user.UserAuthenticationDTO;
import ru.averkiev.greenchat_user.services.impl.AuthenticationServiceImpl;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author mrGreenNV
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    /**
     * Сервис для взаимодействия с аутентификацией.
     */
    private final AuthenticationServiceImpl authenticationService;

    /**
     * Константа заголовка HTTP.
     */
    private static final String AUTHORIZATION = "Authorization";


    /**
     * Устанавливает аутентификацию в контекст на основании токена из запроса.
     * @param request запрос
     * @param response ответ.
     * @param chain фильтр.
     * @throws IOException выбрасывает если возникает ошибка ввода
     * @throws ServletException выбрасывает если возникает ошибка при работе с запросом.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Извлечение токена из запроса.
        String token = getTokenFromRequest((HttpServletRequest) request);

        // Проверка извлечённого токена.
        if (StringUtils.hasText(token) && authenticationService.validateToken(token)) {
            // Получение аутентификации пользователя.
            Authentication authentication = getAuthentication(token);

            // Установка аутентификации в контексте безопасности.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        this.doFilterInternal(request, response, chain);

        // Продолжение выполнения цепочки фильтров.
        chain.doFilter(request, response);
    }

    /**
     * Обновляет токены, если у них подходит срок окончания действия.
     * @param request запрос
     * @param response ответ.
     * @param filterChain фильтр.
     * @throws IOException выбрасывает если возникает ошибка ввода
     * @throws ServletException выбрасывает если возникает ошибка при работе с запросом.
     */
    public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Извлечение токена из запроса.
        String token = getTokenFromRequest((HttpServletRequest) request);

        if (token != null) {
            if (authenticationService.isAccessTokenExpired(token)) {
                System.out.println("В ЭТОМ МЕСТЕ ОБНОВЛЯЕМ ТОКЕНЫ");
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Приватный метод getTokenFromRequest извлекает JSON Web Token (JWT) из заголовка Authorization HTTP-запроса.
     * Если токен присутствует и начинается с префикса "Bearer ", метод возвращает сам токен (без префикса),
     * в противном случае возвращается null.
     *
     * @param servletRequest HTTP-запрос.
     * @return JSON Web Token (JWT) или null, если он не найден в заголовке.
     */
    private String getTokenFromRequest(HttpServletRequest servletRequest) {
        final String bearer = servletRequest.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    /**
     * Создаёт объект аутентификации на основе токена.
     * @param token токен, являющийся основой для аутентификации.
     * @return объект Authentication.
     * @exception AuthException выбрасывает, если аутентификация не пройдена.
     */
    private Authentication getAuthentication(String token) throws AuthException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        ResponseEntity<UserAuthenticationDTO> responseEntity = new RestTemplate().exchange(
                "http://localhost:6060/greenchat/auth" + "/validate",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                UserAuthenticationDTO.class
        );

        UserAuthenticationDTO userAuthenticationDTO = responseEntity.getBody();

        if (userAuthenticationDTO != null) {
            // Создание объекта аутентификации на основе полученных данных.
            return new UsernamePasswordAuthenticationToken(
                    userAuthenticationDTO.getLogin(),
                    null,
                    userAuthenticationDTO.getRoles().stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList())
            );
        }
        throw new AuthException("Аутентификация не пройдена");
    }
}
