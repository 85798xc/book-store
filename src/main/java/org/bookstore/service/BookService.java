package org.bookstore.service;

import org.bookstore.service.model.BookDto;

import java.util.List;

public interface BookService {
    List <BookDto> getAll();
    BookDto getById(Long id);
    void add(BookDto bookDto);
    BookDto changeById(Long id , BookDto bookDto);
    void removeById(Long id);

}
