package org.bookstore.service.model;

import java.util.Objects;
import java.util.Set;

public class UserDto {
    private String username;
    private Set<BookDto> books;

    public UserDto() {
    }

    public UserDto(String username, Set<BookDto> books) {
        this.username = username;
        this.books = books;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<BookDto> getBooks() {
        return books;
    }

    public void setBooks(Set<BookDto> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(username, userDto.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", books=" + books +
                '}';
    }
}
