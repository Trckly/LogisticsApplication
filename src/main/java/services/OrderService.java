package services;

import entities.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class OrderService {

    public List<Order> getAllOrders() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from Order", Order.class).list();
        }
    }

    public List<Order> searchOrders(String query) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from Order where cast(id as string) = :query", Order.class)
                    .setParameter("query", "%" + query + "%")
                    .list();
        }
    }

    public void addOrder(Order order) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(order);
            transaction.commit();
        }
    }

    public void updateOrder(Order order) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(order);
            transaction.commit();
        }
    }

    public void deleteOrder(Order order) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(order);
            transaction.commit();
        }
    }
}