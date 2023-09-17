package org.bookstore.repository.impl;

import org.bookstore.repository.UserRepository;
import org.bookstore.repository.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.query.SelectionQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Component Service Repository RestController Controller
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<List<User>> findAll() {
        try (Session session = sessionFactory.openSession()) {
            SelectionQuery<User> query = session.createSelectionQuery("from User", User.class);
            return Optional.ofNullable(query.getResultList());
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Long> create(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> updateById(Long id, User user) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }
    //banana

    public static void main(String[] args) {
        SessionFactory sessionFactory1 = new Configuration().configure().buildSessionFactory();
        UserRepository userRepository = new UserRepositoryImpl(sessionFactory1);
        System.out.println(userRepository.findAll().orElseThrow());
    }

}
