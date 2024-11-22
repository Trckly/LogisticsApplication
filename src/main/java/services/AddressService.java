package services;

import entities.Address;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class AddressService {

    public List<Address> getAllAddresses() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from Address order by province", Address.class).list();
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

    public void deleteAddress(UUID addressId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();

            // Find the address by ID
            Address address = session.get(Address.class, addressId);
            if (address != null) {
                session.remove(address); // Remove the address from the session
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback if an error occurs
            }
            throw e; // Re-throw the exception
        }
    }
}