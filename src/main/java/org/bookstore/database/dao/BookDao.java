package org.bookstore.database.dao;

import org.bookstore.database.entity.Book;

import java.util.List;

public interface BookDao {
    List<Book> findAll();
    Book findById(Long id);
    Book create (Book book);
    Book updateById(Long id, Book book);
    void deleteById(Long id);

}
