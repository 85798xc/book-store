package org.bookstore.database.dao.impl;

import org.bookstore.database.dao.BookDao;
import org.bookstore.database.entity.Book;
import org.bookstore.database.entity.User;
import org.bookstore.database.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {

    private final ConnectionPool connectionPool;

    public BookDaoImpl() {
        connectionPool = ConnectionPool.getConnectionPool();
    }


    @Override
    public List<Book> findAll() {

        final List<Book> books = new ArrayList<>();
        try (Connection connection = connectionPool.acquireConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books");
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setName(resultSet.getString("username"));
                books.add(book);
            }
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
    public Book findById(Long id) {

        try (Connection connection = connectionPool.acquireConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE id = ?")
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setName(resultSet.getString("name"));
                return book;
            }
            resultSet.close();
            connectionPool.releaseConnection(connection);
            return null; // User with the given ID not found
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book create(Book book) {
        try (Connection connection = connectionPool.acquireConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO books (name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, book.getName());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating book failed, no ID obtained.");
            }
            generatedKeys.close();
            connectionPool.releaseConnection(connection);
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book updateById(Long id, Book book) {
        try (Connection connection = connectionPool.acquireConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE books SET name = ? WHERE id = ?")
        ) {
            preparedStatement.setString(1, book.getName());
            preparedStatement.setLong(2, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating book failed, no rows affected.");
            }
            book.setId(id);
            connectionPool.releaseConnection(connection);
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {

        try (Connection connection = connectionPool.acquireConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM books WHERE id = ?")
        ) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting book failed, no rows affected.");
            }
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
