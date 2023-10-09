package org.bookstore.repository.impl;

import org.bookstore.repository.BookRepository;
import org.bookstore.repository.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    public BookRepositoryImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<List<Book>> findAll() {
        try (Session session = sessionFactory.openSession() ){
            Query<Book> query = session.createQuery("from Book" , Book.class);
            return Optional.ofNullable(query.getResultList());
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Book book = session.get(Book.class, id);
            return Optional.ofNullable(book);
        }
    }

    @Override
    public void create(Book book) {
        try (Session session = sessionFactory.openSession() ){
            session.beginTransaction();
            session.persist(book);
            session.getTransaction().commit();
        }
    }

    @Override
    public Optional<Book> updateById(Long id, Book book) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Book existingBook = session.get(Book.class, id);
            if (existingBook != null) {
                existingBook.setName(book.getName());
                Book updatedBook = session.merge(existingBook);
                transaction.commit();
                return Optional.of(updatedBook);
            } else {
                transaction.rollback();
                return Optional.empty();
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Book book = session.get(Book.class, id);
            if (book != null) {
                session.remove(book);
                transaction.commit();
            } else {
                transaction.rollback();
            }
        }

    }


}
