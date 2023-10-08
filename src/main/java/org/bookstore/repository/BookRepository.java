package org.bookstore.repository;


import org.bookstore.repository.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {


    Optional<List<Book>> findAll();
    Optional<Book> findById(Long id);
    void create (Book book);
    Optional<Book> updateById(Long id, Book book);
    void deleteById(Long id);













}
