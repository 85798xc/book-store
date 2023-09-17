package org.bookstore.repository;

import org.bookstore.repository.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<List<User>> findAll();

    Optional<User> findById(Long id);

    Optional<Long> create(User user);

    Optional<User> updateById(Long id, User user);

    void deleteById(Long id);

}
