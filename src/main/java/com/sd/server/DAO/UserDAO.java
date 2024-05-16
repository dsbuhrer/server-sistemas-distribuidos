package com.sd.server.DAO;

import com.sd.server.Exceptions.EmailAlreadyUsedException;
import com.sd.server.Exceptions.NotFoundException;
import com.sd.server.Models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class UserDAO {

    private final SessionFactory sessionFactory;

    public UserDAO() {
        try {
            Configuration configuration = new Configuration().configure();
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public User addUser(User user) {
        User createdUser = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            System.out.println(user);
            System.out.println("user que chegou ao create");
            session.save(user);
            tx.commit();
            createdUser = session.get(User.class, user.getUuid()); // Recupera o usuário do banco de dados para garantir que tenha todos os valores atualizados, incluindo o ID atribuído pelo banco de dados
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return createdUser;
    }

    public User getUserById(String id) {

        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, UUID.fromString(id));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteUser(String email) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User WHERE email = :email");
            query.setParameter("email",email);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public User getUserByEmail(String email) throws NotFoundException {
        Session session = sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
        User user;
        query.setParameter("email", email);
        if((user= query.uniqueResult()) != null ){
            return user;
        }else {
            return null;
        }
    }

    public boolean addUserIfNotExistByEmail(User user) throws EmailAlreadyUsedException {
        if (isUserExistsByEmail(user.getEmail())) {
            System.out.println("chegou para criar");
            addUser(user);
            return true;
        } else {
            System.out.println("chegou para false");
            return false;
        }
    }

    public void addFirstUser() {
        User user = new User("Admin","admin@mail.com","123456");
        try {
            addUserIfNotExistByEmail(user);
        } catch (EmailAlreadyUsedException ignored) {

        }
    }

    public boolean isUserExistsByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
            query.setParameter("email", email);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}