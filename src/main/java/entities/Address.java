package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "addresses", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "addresses_province_settlement_street_street_number_company__key", columnNames = {"province", "settlement", "street", "street_number", "company_name"})
})
public class Address {
    @Id
    @ColumnDefault("uuid_generate_v4()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "province", nullable = false, length = 50)
    private String province;

    @Column(name = "settlement", nullable = false, length = 50)
    private String settlement;

    @Column(name = "street", nullable = false, length = 50)
    private String street;

    @Column(name = "street_number", nullable = false, length = 50)
    private String streetNumber;

    @Column(name = "company_name", nullable = false, length = 50)
    private String companyName;

    @OneToMany(mappedBy = "address")
    private Set<OrdersAddress> ordersAddresses = new LinkedHashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<OrdersAddress> getOrdersAddresses() {
        return ordersAddresses;
    }

    public void setOrdersAddresses(Set<OrdersAddress> ordersAddresses) {
        this.ordersAddresses = ordersAddresses;
    }

    @Override
    public String toString() {
        return province + ", " + settlement + ", " + street + ", " + streetNumber + ", " + companyName;
    }
}