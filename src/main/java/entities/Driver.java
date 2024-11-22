package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "drivers", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "drivers_first_name_last_name_surname_phone_number_key", columnNames = {"first_name", "last_name", "surname", "phone_number"}),
        @UniqueConstraint(name = "Drivers_driving_license_id_key", columnNames = {"driving_license_id"})
})
public class Driver {
    @Id
    @ColumnDefault("uuid_generate_v4()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(name = "surname", nullable = false, length = 20)
    private String surname;

    @Column(name = "phone_number", nullable = false, length = 13)
    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "driving_license_id", nullable = false)
    private DriverLicense drivingLicense;

    @OneToMany(mappedBy = "driver")
    private Set<Order> orders = new LinkedHashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public DriverLicense getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(DriverLicense drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}