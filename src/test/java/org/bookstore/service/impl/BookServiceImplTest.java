package org.bookstore.service.impl;

import org.bookstore.repository.BookRepository;
import org.bookstore.repository.entity.Book;
import org.bookstore.service.mapper.BookMapper;
import org.bookstore.service.model.BookDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    BookMapper bookMapper;

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookServiceImpl bookServiceImpl;

    BookDto bookDto;
    Book book;

    @BeforeEach
    public void init() {
        bookDto = new BookDto();
        bookDto.setName("TEST");
        book = new Book();
        book.setId(228L);
    }


    @Test
    void getAll() {
        List<Book> books = Collections.singletonList(book);
        Optional<List<Book>> optionalBooks = Optional.of(books);
        List<BookDto> expected = Collections.singletonList(bookDto);

        when(bookRepository.findAll()).thenReturn(optionalBooks);
        when(bookMapper.toDto(books)).thenReturn(expected);

        List<BookDto> result = bookServiceImpl.getAll();

        assertEquals(expected, result);

        verify(bookRepository).findAll();
        verify(bookMapper).toDto(books);
    }

    @Test
    void getById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookServiceImpl.getById(1L);

        assertEquals(bookDto, result);

        verify(bookRepository).findById(1L);
        verify(bookMapper).toDto(book);
    }

    @Test
    void add() {
        when(bookMapper.toEntity(bookDto)).thenReturn(book);


        doNothing().when(bookRepository).create(book);

        bookServiceImpl.add(bookDto);

        verify(bookMapper).toEntity(bookDto);
        verify(bookRepository).create(book);
    }

    @Test
    void changeById() {
        when(bookMapper.toEntity(bookDto)).thenReturn(book);
        when(bookRepository.updateById(1L, book)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookServiceImpl.changeById(1L, bookDto);

        assertEquals(bookDto, result);

        verify(bookMapper).toEntity(bookDto);
        verify(bookRepository).updateById(1L, book);
        verify(bookMapper).toDto(book);
    }

    @Test
    void removeById() {
        bookServiceImpl.removeById(1L);
        verify(bookRepository).deleteById(1L);
    }
}