package org.bookstore.service.mapper.impl;

import org.bookstore.repository.entity.Book;
import org.bookstore.repository.entity.User;
import org.bookstore.service.mapper.UserMapper;
import org.bookstore.service.model.BookDto;
import org.bookstore.service.model.UserDto;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto toDto(User entity) {
        if (entity == null) {
            return null;
        }

        final UserDto userDto = new UserDto();
        userDto.setUsername(entity.getUsername());

        if (entity.getBooks() != null && !entity.getBooks().isEmpty()) {
            Set<BookDto> books = new HashSet<>();
            for (Book entityBook : entity.getBooks()) {
                BookDto bookDto = new BookDto();
                bookDto.setName(entityBook.getName());
                bookDto.setAuthor(entityBook.getAuthor());
                books.add(bookDto);
            }
            userDto.setBooks(books);
        }

        return userDto;
    }

    @Override
    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        final User user = new User();
        user.setUsername(dto.getUsername());

        return user;
    }

    @Override
    public List<UserDto> toDto(List<User> entities) {
        List<UserDto> list = new ArrayList<>();
        for (User user : entities) {
            list.add(toDto(user));
        }
        return list;
    }

    @Override
    public List<User> toEntity(List<UserDto> dtos) {
        return dtos.stream().map(this::toEntity).filter(Objects::nonNull).collect(Collectors.toList());
    }


}










