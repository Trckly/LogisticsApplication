package services;

import entities.DriverLicense;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class DriverLicenseService {
    public List<DriverLicense> getAllDriverLicenses() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from DriverLicense", DriverLicense.class).list();
        }
    }

    public List<DriverLicense> searchDriverLicenses(String query) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from DriverLicense where id like :query", DriverLicense.class)
                    .setParameter("query", "%" + query + "%")
                    .list();
        }
    }

    public void addDriverLicense(DriverLicense license) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(license);
            transaction.commit();
        }
    }

    public void updateDriverLicense(DriverLicense license) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(license);
            transaction.commit();
        }
    }

    public void deleteDriverLicense(DriverLicense license) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(license);
            transaction.commit();
        }
    }
}