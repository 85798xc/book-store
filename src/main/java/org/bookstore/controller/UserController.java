package org.bookstore.controller;

import org.bookstore.service.UserService;
import org.bookstore.service.impl.UserServiceImpl;
import org.bookstore.service.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book-store")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getById(id);
        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, HttpStatus.OK);
        return response;
    }

    public ResponseEntity<Void> addUser (UserDto userDto){

       userService.add(userDto);

    }


}
