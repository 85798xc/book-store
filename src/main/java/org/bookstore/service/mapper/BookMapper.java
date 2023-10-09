package org.bookstore.service.mapper;

import org.bookstore.repository.entity.Book;
import org.bookstore.repository.entity.User;
import org.bookstore.service.model.BookDto;
import org.bookstore.service.model.UserDto;

import java.util.List;

public interface BookMapper {
    BookDto toDto(Book entity);
    Book toEntity(BookDto dto);
    List<BookDto> toDto(List<Book> entities);
    List<Book> toEntity(List<BookDto> dtos);
}

