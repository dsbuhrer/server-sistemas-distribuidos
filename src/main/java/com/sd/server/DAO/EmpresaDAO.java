package com.sd.server.DAO;

import com.sd.server.Exceptions.EmailAlreadyUsedException;
import com.sd.server.Exceptions.NotFoundException;
import com.sd.server.Models.Empresa;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.UUID;


public class EmpresaDAO {

    private final SessionFactory sessionFactory;

    public EmpresaDAO() {
        try {
            Configuration configuration = new Configuration().configure();
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Empresa addEmpresa(Empresa empresa) {
        Empresa createdEmpresa = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            System.out.println(empresa);
            System.out.println("empresa que chegou ao create");
            session.save(empresa);
            tx.commit();
            createdEmpresa = session.get(Empresa.class, empresa.getUuid()); // Recupera o usuário do banco de dados para garantir que tenha todos os valores atualizados, incluindo o ID atribuído pelo banco de dados
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return createdEmpresa;
    }

    public Empresa getEmpresaById(String id) {

        try (Session session = sessionFactory.openSession()) {
            return session.get(Empresa.class, UUID.fromString(id));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Empresa> getAllEmpresas() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Empresa", Empresa.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateEmpresa(Empresa empresa) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(empresa);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteEmpresa(String email) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Empresa WHERE email = :email");
            query.setParameter("email",email);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Empresa getEmpresaByEmail(String email) throws NotFoundException {
        Session session = sessionFactory.openSession();
        Query<Empresa> query = session.createQuery("FROM Empresa WHERE email = :email", Empresa.class);
        Empresa empresa;
        query.setParameter("email", email);
        if((empresa = query.uniqueResult()) != null ){
            return empresa;
        }else {
            return null;
        }
    }

    public boolean addEmpresaIfNotExistByEmail(Empresa empresa) throws EmailAlreadyUsedException {
        if (isEmpresaExistsByEmail(empresa.getEmail())) {
            System.out.println("chegou para criar");
            addEmpresa(empresa);
            return true;
        } else {
            System.out.println("chegou para false");
            return false;
        }
    }

    public void addFirstEmpresa() {
        Empresa empresa = new Empresa("Admin","admin@mail.com","123456","9999999999","Descrição","Ramo");
        try {
            addEmpresaIfNotExistByEmail(empresa);
        } catch (EmailAlreadyUsedException ignored) {

        }
    }

    public boolean isEmpresaExistsByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<Empresa> query = session.createQuery("FROM Empresa WHERE email = :email", Empresa.class);
            query.setParameter("email", email);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isEmpresaExistsByCnpj(String cnpj) {
        try (Session session = sessionFactory.openSession()) {
            Query<Empresa> query = session.createQuery("FROM Empresa WHERE cnpj = :cnpj", Empresa.class);
            query.setParameter("cnpj", cnpj);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}