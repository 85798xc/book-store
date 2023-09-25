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

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }

    @Override
    public Optional<List<User>> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User", User.class);
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
        try (Session session = sessionFactory.openSession()) {
            session.persist(user);
        }
    }

    @Override
    public Optional<User> updateById(Long id, User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User existingUser = session.get(User.class, id);
            if (existingUser != null) {
                user.setId(id); // Make sure the user object has the correct ID
                session.merge(user); // Use merge to update the entity
                transaction.commit();
                return Optional.of(user);
            } else {
                transaction.rollback();
                return Optional.empty(); // User not found
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
    }}


    public static void main(String[] args) {
        SessionFactory sessionFactory1 = new Configuration().configure().buildSessionFactory();
        UserRepository userRepository = new UserRepositoryImpl(sessionFactory1);
        System.out.println(userRepository.findAll().orElseThrow());
        User user = new User();
        user.setUsername("test");
        userRepository.create(user);
    }

}
