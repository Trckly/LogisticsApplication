package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "vehicles", schema = "public", indexes = {
        @Index(name = "idx_vehicle_model_hash", columnList = "model")
})
public class Vehicle {
    @Id
    @Column(name = "license_plate", nullable = false, length = 8)
    private String licensePlate;

    @Column(name = "model", length = 20)
    private String model;

    @Column(name = "production_year", nullable = false)
    private Integer productionYear;

    @ColumnDefault("24.00")
    @Column(name = "carrying_capacity", nullable = false)
    private Double carryingCapacity;

    @OneToMany(mappedBy = "vehicle")
    private Set<Order> orders = new LinkedHashSet<>();

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public Double getCarryingCapacity() {
        return carryingCapacity;
    }

    public void setCarryingCapacity(Double carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return licensePlate;
    }
}