package org.bookstore.service.mapper;

import org.bookstore.repository.entity.User;
import org.bookstore.service.model.UserDto;

import java.util.List;

public interface UserMapper {
    UserDto toDto(User entity);
    User toEntity(UserDto dto);
    List<UserDto> toDto(List<User> entities);
    List<User> toEntity(List<UserDto> dtos);
}
