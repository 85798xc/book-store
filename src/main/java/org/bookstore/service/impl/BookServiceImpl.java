package org.bookstore.service.impl;

import org.bookstore.repository.BookRepository;
import org.bookstore.repository.entity.Book;
import org.bookstore.repository.impl.BookRepositoryImpl;
import org.bookstore.service.BookService;
import org.bookstore.service.mapper.BookMapper;
import org.bookstore.service.mapper.impl.BookMapperImpl;
import org.bookstore.service.model.BookDto;
import org.hibernate.cfg.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookDto> getAll() {
        Optional<List<Book>> optionalBooks = bookRepository.findAll();
        List<Book> entities = optionalBooks.orElseThrow(() -> new RuntimeException("No books"));
        List<BookDto> bookDtos = bookMapper.toDto(entities);
        return bookDtos;
    }

    @Override
    public BookDto getById(Long id) {
        return null;
    }

    @Override
    public void add(BookDto bookDto) {

    }

    @Override
    public BookDto changeById(Long id, BookDto bookDto) {
        return null;
    }

    @Override
    public void removeById(Long id) {

    }

    public static void main(String[] args) {
        BookService bookService = new BookServiceImpl(new BookRepositoryImpl(new Configuration().configure().buildSessionFactory()), new BookMapperImpl());
        System.out.println(bookService.getAll());
    }
}
