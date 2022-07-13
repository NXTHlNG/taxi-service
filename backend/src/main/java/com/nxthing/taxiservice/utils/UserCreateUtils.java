package com.nxthing.taxiservice.utils;

import com.nxthing.taxiservice.dto.SignUpDto;
import com.nxthing.taxiservice.entity.User;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;

public class UserCreateUtils {
    public static <T extends User> T createUser(SignUpDto dto, Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return ReflectionUtils.accessibleConstructor(
                        clazz,
                        dto.getUsername().getClass(),
                        dto.getFirstName().getClass(),
                        dto.getLastName().getClass(),
                        dto.getEmail().getClass(),
                        dto.getPassword().getClass()
                )
                .newInstance(
                        dto.getUsername(),
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getEmail(),
                        dto.getPassword()
                );
    }
}
