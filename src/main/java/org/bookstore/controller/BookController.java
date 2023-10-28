package org.bookstore.controller;


import org.bookstore.service.BookService;
import org.bookstore.service.model.BookDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/book-store")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookDto bookDto = bookService.getById(id);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<Void> addBook(@RequestBody BookDto bookDto) {
        bookService.add(bookDto);
        return ResponseEntity.ok().build();

    }

    @PutMapping("/books/{id}")
    public ResponseEntity<BookDto> updateBookById(@PathVariable Long id, @RequestBody BookDto bookDto) {
        bookService.changeById(id, bookDto);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.removeById(id);
        return ResponseEntity.ok().build();
    }


}
