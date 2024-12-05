package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address {
    @Id
    @Column(name = "add_id", nullable = false, columnDefinition = "bigint(20)")
    private Long id;

    @Column(name = "country", columnDefinition = "smallint(6)")
    private Short country;

    @Column(name = "zipcode", columnDefinition = "varchar(7)")
    private String zipcode;

    @Column(name = "number", columnDefinition = "varchar(20)")
    private String number;

    @Column(name = "city", columnDefinition = "varchar(50)")
    private String city;

    @Column(name = "street", columnDefinition = "varchar(150)")
    private String street;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}