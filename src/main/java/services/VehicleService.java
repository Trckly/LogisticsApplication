package services;

import entities.Vehicle;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class VehicleService {

    public List<Vehicle> getAllVehicles() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from Vehicle order by licensePlate", Vehicle.class).list();
        }
    }

    public List<Vehicle> searchVehicles(String query) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from Vehicle where licensePlate like :query or model like :query", Vehicle.class)
                    .setParameter("query", "%" + query + "%")
                    .list();
        }
    }

    public void addVehicle(Vehicle vehicle) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(vehicle);
            transaction.commit();
        }
    }

    public void updateVehicle(Vehicle vehicle) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(vehicle);
            transaction.commit();
        }
    }

    public void deleteVehicle(Vehicle vehicle) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(vehicle);
            transaction.commit();
        }
    }
}