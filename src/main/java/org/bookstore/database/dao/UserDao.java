package org.bookstore.database.dao;

import org.bookstore.database.entity.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();
    User findById(Long id);
    User create (User user);
    User updateById(Long id, User user);
    void deleteById(Long id);

}
