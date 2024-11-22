package services;

import entities.Driver;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class DriverService {

    public List<Driver> getAllDrivers() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from Driver order by firstName", Driver.class).list();
        }
    }

    public List<Driver> searchDrivers(String query) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from Driver where firstName like :query or lastName like :query", Driver.class)
                    .setParameter("query", "%" + query + "%")
                    .list();
        }
    }

    public void addDriver(Driver driver) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(driver);
            transaction.commit();
        }
    }

    public void updateDriver(Driver driver) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(driver);
            transaction.commit();
        }
    }

    public void deleteDriver(Driver driver) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(driver);
            transaction.commit();
        }
    }
}