package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "cargo", schema = "public")
public class Cargo {
    @Id
    @ColumnDefault("uuid_generate_v4()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "denomination", nullable = false, length = 50)
    private String denomination;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "volume", nullable = false)
    private Double volume;

    @OneToMany(mappedBy = "cargo")
    private Set<OrdersCargo> ordersCargos = new LinkedHashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Set<OrdersCargo> getOrdersCargos() {
        return ordersCargos;
    }

    public void setOrdersCargos(Set<OrdersCargo> ordersCargos) {
        this.ordersCargos = ordersCargos;
    }

    @Override
    public String toString() {
        return denomination + " " + String.format("%.2f", weight) + " " + String.format("%.2f", volume);
    }
}