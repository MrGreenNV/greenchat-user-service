package ru.averkiev.greenchat_user.utils;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.averkiev.greenchat_user.models.Role;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс расширяет ModelMapper, для реализации метода преобразования Role в String.
 * @author mrGreenNV
 */
@Component
public class CustomModelMapper extends ModelMapper {

    public CustomModelMapper() {
        super();
        addConverter(roleToSetStringConverter());
    }
    private Converter<List<Role>, List<String>> roleToSetStringConverter() {
        return new AbstractConverter<>() {
            @Override
            protected List<String> convert(List<Role> roles) {
                return roles.stream()
                        .map(Role::getRoleName).collect(Collectors.toList());
            }
        };
    }
}
