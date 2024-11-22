package services;

import entities.Address;
import entities.OrdersAddress;
import entities.enums.AddressType;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class OrdersAddressService {

    public List<Address> getInitialAddresses() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery(
                    "select oa.address from OrdersAddress oa where oa.addressType = 'initial'", Address.class
            ).list();
        }
    }

    public List<Address> getDestinationAddresses() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery(
                    "select oa.address from OrdersAddress oa where oa.addressType = 'destination'", Address.class
            ).list();
        }
    }

    public void addOrderAddress(OrdersAddress orderAddress) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(orderAddress);
            transaction.commit();
        }
    }
}