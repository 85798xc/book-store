package org.bookstore.service.impl;

import org.bookstore.repository.UserRepository;
import org.bookstore.repository.entity.User;
import org.bookstore.repository.impl.UserRepositoryImpl;
import org.bookstore.service.UserService;
import org.bookstore.service.mapper.UserMapper;
import org.bookstore.service.mapper.impl.UserMapperImpl;
import org.bookstore.service.model.UserDto;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> getAll() {
        Optional<List<User>> optionalUsers = userRepository.findAll();
        List<User> entities = optionalUsers.orElseThrow(() -> new RuntimeException("No users"));
        List<UserDto> userDtos = userMapper.toDto(entities);
        return userDtos;
    }

    @Override
    public UserDto getById(Long id) {
        return null;
    }

    @Override
    public void add(UserDto userDto) {

    }

    @Override
    public UserDto changeById(Long id, UserDto userDto) {
        return null;
    }

    @Override
    public void removeById(Long id) {

    }

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl(new UserRepositoryImpl(new Configuration().configure().buildSessionFactory()), new UserMapperImpl());

        System.out.println(userService.getAll());
    }
}
