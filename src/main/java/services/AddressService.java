package services;

import entities.Address;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class AddressService {

    public List<Address> getAllAddresses() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from Address", Address.class).list();
        }
    }

    public List<Address> searchAddresses(String query) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from Address where street like :query", Address.class)
                    .setParameter("query", "%" + query + "%").list();
        }
    }

    public void addAddress(Address address) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(address);
            transaction.commit();
        }
    }
}