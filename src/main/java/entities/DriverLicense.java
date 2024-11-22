package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "driver_licenses", schema = "public")
public class DriverLicense {
    @Id
    @Column(name = "id", nullable = false, length = 10)
    private String id;

    @Column(name = "issuer", nullable = false, length = 20)
    private String issuer;

    @Column(name = "date_issued", nullable = false)
    private LocalDate dateIssued;

    @OneToOne(mappedBy = "drivingLicense")
    private Driver driver;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public LocalDate getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(LocalDate dateIssued) {
        this.dateIssued = dateIssued;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

}