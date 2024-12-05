package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;

@Entity
@Table(name = "company")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Company {
    @Id
    @Column(name = "comp_id", nullable = false, columnDefinition = "bigint(20)")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address", nullable = false, columnDefinition = "bigint(20)")
    @ToString.Exclude
    private Address address;

    @Column(name = "about", columnDefinition = "varchar(2000)")
    private String about;

    @Column(name = "comp_name", columnDefinition = "varchar(255)")
    private String compName;

    @Column(name = "email", columnDefinition = "varchar(255)")
    private String email;

    @Column(name = "phone", columnDefinition = "varchar(255)")
    private String phone;

    @Column(name = "web_url", columnDefinition = "varchar(255)")
    private String webUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}