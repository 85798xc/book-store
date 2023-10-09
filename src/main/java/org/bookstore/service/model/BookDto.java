package org.bookstore.service.model;

import java.util.Objects;

public class BookDto {
    private String author;
    private String name;

    public BookDto(String author, String name) {
        this.author = author;
        this.name = name;
    }

    public BookDto() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookDto)) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(author, bookDto.author) && Objects.equals(name, bookDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, name);
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "author='" + author + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
