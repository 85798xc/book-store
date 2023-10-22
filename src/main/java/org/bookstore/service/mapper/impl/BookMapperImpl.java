package org.bookstore.service.mapper.impl;

import org.bookstore.repository.entity.Book;
import org.bookstore.service.mapper.BookMapper;
import org.bookstore.service.model.BookDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class BookMapperImpl implements BookMapper {
    @Override
    public BookDto toDto(Book entity) {
        if (entity == null) {
            return null;
        }

        final BookDto bookDto = new BookDto();
        bookDto.setName(entity.getName());
        bookDto.setAuthor(entity.getAuthor());

        return bookDto;
    }

    @Override
    public Book toEntity(BookDto dto) {
        if (dto == null){
            return null;
        }
        final Book book = new Book();
        book.setName(dto.getName());
        book.setAuthor(dto.getAuthor());

        return book;
    }

    @Override
    public List<BookDto> toDto(List<Book> entities) {
        List<BookDto> list = new ArrayList<>();
        for (Book book : entities){
            list.add(toDto(book));
        }
        return null;
    }

    @Override
    public List<Book> toEntity(List<BookDto> dtos) {
        return dtos.stream().map(this::toEntity).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
