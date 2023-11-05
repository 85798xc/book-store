package org.bookstore;


import org.bookstore.repository.BookRepository;
import org.bookstore.repository.UserRepository;
import org.bookstore.repository.entity.Book;
import org.bookstore.repository.entity.User;
import org.bookstore.repository.impl.BookRepositoryImpl;
import org.bookstore.repository.impl.UserRepositoryImpl;
import org.bookstore.service.BookService;
import org.bookstore.service.UserService;
import org.bookstore.service.impl.BookServiceImpl;
import org.bookstore.service.impl.UserServiceImpl;
import org.bookstore.service.mapper.BookMapper;
import org.bookstore.service.mapper.UserMapper;
import org.bookstore.service.mapper.impl.BookMapperImpl;
import org.bookstore.service.mapper.impl.UserMapperImpl;
import org.bookstore.service.model.BookDto;
import org.bookstore.service.model.UserDto;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class BookstoreIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");
    static BookService bookService;
    static UserService userService;
    static BookMapper bookMapper;
    static UserMapper userMapper;
    static BookRepository bookRepository;
    static UserRepository userRepository;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        properties.put("hibernate.connection.driver_class", postgres.getDriverClassName());
        properties.put("hibernate.connection.password", postgres.getPassword());
        properties.put("hibernate.connection.url", postgres.getJdbcUrl());
        properties.put("hibernate.connection.username", postgres.getUsername());
        properties.put("show_sql", true);
        properties.put("format_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        configuration.addProperties(properties);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Book.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        bookMapper = new BookMapperImpl();
        bookRepository = new BookRepositoryImpl(sessionFactory);
        bookService = new BookServiceImpl(bookRepository, bookMapper);
        userMapper = new UserMapperImpl();
        userRepository = new UserRepositoryImpl(sessionFactory);
        userService = new UserServiceImpl(userRepository, userMapper);
    }

    @AfterAll
    static void afterAll() {
        postgres.close();
    }

    @Test
    void bookStoreIntegrationTest() {
        // test add() and getById()
        UserDto userToAdd = new UserDto();
        userToAdd.setUsername("Test");
        userService.add(userToAdd);
        UserDto addedUser = userService.getById(1L);

        assertEquals(userToAdd, addedUser);

        //test getAll()
        assertEquals(userService.getAll(), Collections.singletonList(userToAdd));

        //test changeById()
        UserDto userToChange = new UserDto();
        userToChange.setUsername("Test2");
        userService.changeById(1L, userToChange);

        assertEquals(userToChange, userService.getById(1L));

        //test removeById()
        userToAdd.setUsername("Test3");
        userService.add(userToAdd);
        UserDto addedUser2 = null;
        try {
            userService.removeById(2L);
            assertNull(userService.getById(2L));
        } catch (RuntimeException e) {
            assertEquals("User not found with id: 2", e.getMessage());
        }

        /*Books
        test add() and getById()*/
        BookDto bookToAdd = new BookDto();
        bookToAdd.setName("Test");
        bookService.add(bookToAdd);
        BookDto addedBook = bookService.getById(1L);

        assertEquals(bookToAdd, addedBook);

        //test getAll()
        assertEquals(bookService.getAll(), Collections.singletonList(bookToAdd));


        //test changeByID()
        BookDto bookToChange = new BookDto();
        bookToChange.setName("Test2");
        bookService.changeById(1L, bookToChange);

        assertEquals(bookToChange, bookService.getById(1L));

        //test removeById()
        bookToAdd.setName("Test3");
        bookService.add(bookToAdd);
        BookDto addedBook2 = null;
        try {
            bookService.removeById(2L);
            assertNull(bookService.getById(2L));
        } catch (RuntimeException e) {
            assertEquals("Book not found with id: 2", e.getMessage());
        }

        //test toDto()
        List<Book> bookEntities = new ArrayList<>();
        bookEntities.add(new Book());
        bookEntities.add(new Book());
        List<BookDto> result12 = bookMapper.toDto(bookEntities);
        assertEquals(bookEntities.size(), result12.size());


        //test equals()
        BookDto bookDto1 = new BookDto("Author", "BookName1");
        BookDto bookDto2 = new BookDto("Author", "BookName1");
        BookDto differentBookDto = new BookDto("AnotherAuthor", "AnotherBookName");
        assertEquals(bookDto1, bookDto2);
        assertNotEquals(bookDto1, differentBookDto);

        //test equals
        UserDto userDto1 = new UserDto();
        userDto1.setUsername("TestUser1");
        UserDto userDto2 = new UserDto();
        userDto2.setUsername("TestUser1");
        UserDto differentUserDto = new UserDto();
        differentUserDto.setUsername("DifferentUser");
        assertEquals(userDto1, userDto2);
        assertNotEquals(userDto1, differentUserDto);


    }

}
