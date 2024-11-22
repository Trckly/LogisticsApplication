package services;

import entities.Cargo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class CargoService {

    public List<Cargo> getAllCargo() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from Cargo", Cargo.class).list();
        }
    }

    public List<Cargo> searchCargo(String query) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from Cargo where denomination like :query", Cargo.class)
                    .setParameter("query", "%" + query + "%")
                    .list();
        }
    }

    public void addCargo(Cargo cargo) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(cargo);
            transaction.commit();
        }
    }

    public void updateCargo(Cargo cargo) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(cargo);
            transaction.commit();
        }
    }

    public void deleteCargo(Cargo cargo) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(cargo);
            transaction.commit();
        }
    }
}