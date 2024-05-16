package com.sd.server.DAO;

import com.sd.server.Models.JWTSession;
import com.sd.server.Models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;


public class JWTSessionDAO {

    private final SessionFactory sessionFactory;

    public JWTSessionDAO() {
        try {
            Configuration configuration = new Configuration().configure();
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void addJWTSession(JWTSession jwt_session) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(jwt_session);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<JWTSession> getAllSessions() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM JWTSession ", JWTSession.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void deleteAllJWTSession() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("DELETE FROM JWTSession WHERE TRUE").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAllJWTSessionByIp(String skt_ip) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM JWTSession WHERE ip like :ip");
            query.setParameter("ip",skt_ip);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAllUserJWTSession(Long user_id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM JWTSession WHERE user.id = :user_id");
            query.setParameter("user_id",user_id);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(String token){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Query<JWTSession> query = session.createQuery("FROM JWTSession WHERE token like :token", JWTSession.class);

            query.setParameter("token",token);
            query.setMaxResults(1);
            JWTSession firstRecord = (JWTSession) query.uniqueResult();
            if (firstRecord != null) {
                session.delete(firstRecord);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasUserActiveSession(Long user_id) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("SELECT COUNT(*) from JWTSession where user.id = :user_id");
            query.setParameter("user_id",user_id);
            Long count = (Long)query.uniqueResult();
            return count >= 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}