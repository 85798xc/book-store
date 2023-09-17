package org.bookstore.database.dao.impl;

import org.bookstore.database.dao.UserDao;
import org.bookstore.database.entity.User;
import org.bookstore.database.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private final ConnectionPool connectionPool;

    public UserDaoImpl() {
        connectionPool = ConnectionPool.getConnectionPool();
    }

    @Override
    public List<User> findAll() {
        final List<User> users = new ArrayList<>();
        try (Connection connection = connectionPool.acquireConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                users.add(user);
            }
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User findById(Long id) {

        try (Connection connection = connectionPool.acquireConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?")
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                return user;
            }
            resultSet.close();
            connectionPool.releaseConnection(connection);
            return null; // User with the given ID not found
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public User create(User user) {

        try (Connection connection = connectionPool.acquireConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, user.getUsername());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
            generatedKeys.close();
            connectionPool.releaseConnection(connection);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public User updateById(Long id, User user) {

        try (Connection connection = connectionPool.acquireConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET username = ? WHERE id = ?")
        ) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setLong(2, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
            user.setId(id);
            connectionPool.releaseConnection(connection);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteById(Long id) {

        try (
                Connection connection = connectionPool.acquireConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?")
        ) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {

        UserDaoImpl userDao = new UserDaoImpl();
        System.out.println(userDao.findAll());
        // System.out.println(userDao.findById(1L));
        //userDao.deleteById(1L);

    }
}
