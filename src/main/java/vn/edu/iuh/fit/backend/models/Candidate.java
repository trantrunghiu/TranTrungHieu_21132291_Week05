package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "candidate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Candidate {
    @Id
    @Column(name = "can_id", nullable = false, columnDefinition = "bigint(20)")
    private Long id;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "address", nullable = false, columnDefinition = "bigint(20)")
    private Address address;

    @Column(name = "phone", columnDefinition = "varchar(15)")
    private String phone;

    @Column(name = "email", columnDefinition = "varchar(255)")
    private String email;

    @Column(name = "full_name", columnDefinition = "varchar(255)")
    private String fullName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return Objects.equals(id, candidate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}