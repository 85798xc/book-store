package org.bookstore.repository.impl;


import org.bookstore.repository.UserRepository;
import org.bookstore.repository.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    public UserRepositoryImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }



    @Override
    public Optional<List<User>> findAll() {
        try (Session session = sessionFactory.openSession() ){
            Query<User> query = session.createQuery("from User" , User.class);
            return Optional.ofNullable(query.getResultList());
        }


    }

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        }

    }

    @Override
    public void create(User user) {
        try (Session session = sessionFactory.openSession() ){
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
    }



    @Override
    public Optional<User> updateById(Long id, User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User existingUser = session.get(User.class, id);
            if (existingUser != null) {
                existingUser.setUsername(user.getUsername());
                User updatedUser = session.merge(existingUser);
                transaction.commit();
                return Optional.of(updatedUser);
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
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
                transaction.commit();
            } else {
                transaction.rollback();
            }
        }
    }

}
