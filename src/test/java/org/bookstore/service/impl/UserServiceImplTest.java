package org.bookstore.service.impl;

import org.bookstore.repository.UserRepository;
import org.bookstore.repository.entity.User;
import org.bookstore.service.mapper.UserMapper;
import org.bookstore.service.model.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserMapper userMapper;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userServiceImpl;
    UserDto userDto;
    User user;

    @BeforeEach
    public void init() {
        userDto = new UserDto();
        userDto.setUsername("TEST");
        user = new User();
        user.setUsername("TEST");
        user.setId(1L);
    }


    @Test
    void getAll() {
        List<User> users = Collections.singletonList(user);
        Optional<List<User>> optionalUsers = Optional.of(users);
        List<UserDto> expected = Collections.singletonList(userDto);

        when(userRepository.findAll()).thenReturn(optionalUsers);
        when(userMapper.toDto(users)).thenReturn(expected);

        List<UserDto> result = userServiceImpl.getAll();

        assertEquals(expected, result);

        verify(userRepository).findAll();
        verify(userMapper).toDto(users);
    }

    @Test
    void getById() {

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userServiceImpl.getById(1L);

        assertEquals(userDto, result);

        verify(userRepository).findById(1L);
        verify(userMapper).toDto(user);
    }

    @Test
    void add() {
        when(userMapper.toEntity(userDto)).thenReturn(user);


        doNothing().when(userRepository).create(user);

        userServiceImpl.add(userDto);

        verify(userMapper).toEntity(userDto);
        verify(userRepository).create(user);
    }

    @Test
    void changeById() {
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.updateById(1L, user)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userServiceImpl.changeById(1L, userDto);

        assertEquals(userDto, result);

        verify(userMapper).toEntity(userDto);
        verify(userRepository).updateById(1L, user);
        verify(userMapper).toDto(user);
    }

    @Test
    void removeById() {
        userServiceImpl.removeById(1L);
        verify(userRepository).deleteById(1L);
    }
}