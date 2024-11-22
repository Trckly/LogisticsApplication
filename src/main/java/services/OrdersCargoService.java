package services;

import entities.Cargo;
import entities.OrdersCargo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class OrdersCargoService {

    public List<Cargo> getCargosByOrderId(Long orderId) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery(
                    "select oc.cargo from OrdersCargo oc where oc.order.id = :orderId", Cargo.class
            ).setParameter("orderId", orderId).list();
        }
    }

    public List<OrdersCargo> getAllOrdersCargos() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from OrdersCargo", OrdersCargo.class).list();
        }
    }

    public void addOrdersCargo(OrdersCargo ordersCargo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.persist(ordersCargo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void deleteOrdersCargo(Long ordersCargoId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            OrdersCargo ordersCargo = session.get(OrdersCargo.class, ordersCargoId);
            if (ordersCargo != null) {
                session.remove(ordersCargo);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}