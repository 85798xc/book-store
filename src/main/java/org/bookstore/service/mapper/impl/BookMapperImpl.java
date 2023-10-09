package org.bookstore.service.mapper.impl;

import org.bookstore.repository.entity.Book;
import org.bookstore.repository.entity.User;
import org.bookstore.service.mapper.BookMapper;
import org.bookstore.service.model.BookDto;
import org.bookstore.service.model.UserDto;

import java.util.List;

public class BookMapperImpl implements BookMapper {
    @Override
    public BookDto toDto(Book entity) {
        if (entity == null) {
            return null;
        }
        final BookDto bookDto = new BookDto();
        bookDto.setName(entity.getName());

        return bookDto;
    }

    @Override
    public Book toEntity(BookDto dto) {
        return null;
    }

    @Override
    public List<BookDto> toDto(List<Book> entities) {
        return null;
    }

    @Override
    public List<Book> toEntity(List<BookDto> dtos) {
        return null;
    }
}
