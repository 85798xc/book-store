package org.bookstore.service;

import org.bookstore.service.model.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();
    UserDto getById(Long id);
    void add(UserDto userDto);
    UserDto changeById(Long id , UserDto userDto);
    void removeById(Long id);
}
