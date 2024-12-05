package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "experience")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Experience {
    @Id
    @Column(name = "exp_id", nullable = false, columnDefinition = "bigint(20)")
    private Long id;

    @Column(name = "from_date", length = 20, nullable = false)
    private java.sql.Date fromDate;

    @Column(name = "to_date", nullable = false)
    private java.sql.Date toDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate")
    @ToString.Exclude
    private Candidate candidate;

    @Column(name = "role", columnDefinition = "varchar(100)")
    private String role;

    @Column(name = "company", columnDefinition = "varchar(120)")
    private String company;

    @Column(name = "work_desc", columnDefinition = "varchar(400)")
    private String workDesc;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
