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
        return userMapper.toDto(entities);
    }

    @Override
    public UserDto getById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.toDto(user);
    }

    @Override
    public void add(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        userRepository.create(user);
    }

    @Override
    public UserDto changeById(Long id, UserDto userDto) {
        User updatedUser = userMapper.toEntity(userDto);

        Optional<User> optionalUpdatedUser = userRepository.updateById(id, updatedUser);

        if (optionalUpdatedUser.isPresent()) {

            return userMapper.toDto(optionalUpdatedUser.get());
        } else {

            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Override
    public void removeById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found with id: " + id));


        userRepository.deleteById(id);

    }

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl(new UserRepositoryImpl(new Configuration().configure().buildSessionFactory()), new UserMapperImpl());



        System.out.println(userService.getAll());
    }
}
