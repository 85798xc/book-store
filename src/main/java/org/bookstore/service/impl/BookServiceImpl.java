package org.bookstore.service.impl;

import org.bookstore.repository.BookRepository;
import org.bookstore.repository.entity.Book;
import org.bookstore.repository.entity.User;
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
        return bookMapper.toDto(entities);
    }

    @Override
    public BookDto getById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book book = optionalBook.orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public void add(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        bookRepository.create(book);

    }

    @Override
    public BookDto changeById(Long id, BookDto bookDto) {
        Book updatedBook = bookMapper.toEntity(bookDto);

        Optional<Book> optionalUpdatedBook = bookRepository.updateById(id, updatedBook);

        if (optionalUpdatedBook.isPresent()) {

            return bookMapper.toDto(optionalUpdatedBook.get());
        } else {

            throw new RuntimeException("Book not found with id: " + id);
        }
    }

    @Override
    public void removeById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book book = optionalBook.orElseThrow(()-> new RuntimeException("Book not found with id: \" + id"));
        bookRepository.deleteById(id);

    }

    public static void main(String[] args) {
        BookService bookService = new BookServiceImpl(new BookRepositoryImpl(new Configuration().configure().buildSessionFactory()), new BookMapperImpl());
        System.out.println(bookService.getAll());

        System.out.println(bookService.getById(1L));


    }
}
