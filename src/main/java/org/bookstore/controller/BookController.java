package org.bookstore.controller;


import org.bookstore.service.BookService;
import org.bookstore.service.model.BookDto;
import org.bookstore.service.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-store")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){this.bookService = bookService;}


    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookDto bookDto = bookService.getById(id);
        ResponseEntity<BookDto> response = new ResponseEntity<>(bookDto, HttpStatus.OK);
        return response;
    }

    @PostMapping("/addBook")
    public ResponseEntity<Void> addBook (BookDto bookDto){

        bookService.add(bookDto);
        return ResponseEntity.created(null).build();

    }
    @PutMapping ("/updateBookById")
    public ResponseEntity<BookDto> updateBookById(@PathVariable Long id , BookDto bookDto){

        bookService.changeById(id , bookDto);
        ResponseEntity<BookDto> response = new ResponseEntity<>(bookDto, HttpStatus.OK);
        return response;
    }
    @DeleteMapping("/deleteBook")
    public ResponseEntity<Void> deleteBook (@PathVariable Long id){
        bookService.removeById(id);
        return ResponseEntity.created(null).build();
    }














}
