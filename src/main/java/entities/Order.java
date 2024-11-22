package entities;

import entities.enums.OrderStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "orders", schema = "public", indexes = {
        @Index(name = "idx_orders_client_vehicle", columnList = "client_price, vehicle_id")
})
public class Order {
    @Id
    @ColumnDefault("uuid_generate_v4()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "client_company_name", nullable = false, length = 50)
    private String clientCompanyName;

    @Column(name = "carrier_company_name", nullable = false, length = 50)
    private String carrierCompanyName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "logist_id", nullable = false)
    private Logist logist;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "client_price", nullable = false, precision = 10, scale = 4)
    private BigDecimal clientPrice;

    @Column(name = "carrier_price", nullable = false, precision = 10, scale = 4)
    private BigDecimal carrierPrice;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    private Set<OrdersAddress> ordersAddresses = new LinkedHashSet<>();
    @OneToMany(mappedBy = "order")
    private Set<OrdersCargo> ordersCargos = new LinkedHashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getClientCompanyName() {
        return clientCompanyName;
    }
    public void setClientCompanyName(String clientCompanyName) {
        this.clientCompanyName = clientCompanyName;
    }
    public String getCarrierCompanyName() {
        return carrierCompanyName;
    }
    public void setCarrierCompanyName(String carrierCompanyName) {
        this.carrierCompanyName = carrierCompanyName;
    }
    public Logist getLogist() {
        return logist;
    }
    public void setLogist(Logist logist) {
        this.logist = logist;
    }
    public Driver getDriver() {
        return driver;
    }
    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    public String getVehicleLicensePlate() {
        return vehicle != null ? vehicle.getLicensePlate() : null;
    }
    public BigDecimal getClientPrice() {
        return clientPrice;
    }
    public void setClientPrice(BigDecimal clientPrice) {
        this.clientPrice = clientPrice;
    }
    public BigDecimal getCarrierPrice() {
        return carrierPrice;
    }
    public void setCarrierPrice(BigDecimal carrierPrice) {
        this.carrierPrice = carrierPrice;
    }
    public Set<OrdersAddress> getOrdersAddresses() {
        return ordersAddresses;
    }

    public void setOrdersAddresses(Set<OrdersAddress> ordersAddresses) {
        this.ordersAddresses = ordersAddresses;
    }

    public Set<OrdersCargo> getOrdersCargos() {
        return ordersCargos;
    }
    public void setOrdersCargos(Set<OrdersCargo> ordersCargos) {
        this.ordersCargos = ordersCargos;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return id + ", " + clientCompanyName + ", " + carrierCompanyName + ", " + logist.getId() + ", " +
                driver.getId() + ", " + vehicle.getLicensePlate() + ", " + clientPrice + ", " + carrierPrice + ", " + status + "\n";
    }

/*
 TODO [Reverse Engineering] create field to map the 'status' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "status", columnDefinition = "order_status")
    private Object status;
*/
}