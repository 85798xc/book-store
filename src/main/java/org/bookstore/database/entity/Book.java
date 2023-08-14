package org.bookstore.database.entity;

import java.util.Objects;

public class Book {
    private Long id;
    private static int ded = 20;
    private String name;
    private String author;
    private Long userId;

    public Book() {};

    public Book(Long id, String name, String author, Long userId) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return id.equals(book.id) && name.equals(book.name) && author.equals(book.author) && userId.equals(book.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, userId);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", userId=" + userId +
                '}';
    }
}
