package services;

import entities.Logist;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class LogistService {

    public List<Logist> getAllLogists() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from Logist order by firstName", Logist.class).list();
        }
    }

    public List<Logist> searchLogists(String query) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from Logist where concat(firstName, ' ', lastName) like :query or email like :query", Logist.class)
                    .setParameter("query", "%" + query + "%")
                    .list();
        }
    }

    public void addLogist(Logist logist) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(logist);
            transaction.commit();
        }
    }

    public void updateLogist(Logist logist) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(logist);
            transaction.commit();
        }
    }

    public void deleteLogist(Logist logist) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(logist);
            transaction.commit();
        }
    }
}