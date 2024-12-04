package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
@Entity
@Table(name = "experience")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Experience {
    @Id
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "from_date", length = 20, nullable = false)
    private java.sql.Date from_date;

    @Column(name = "to_date", nullable = false)
    private java.sql.Date to_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate")
    @ToString.Exclude
    private Candidate candidate;

    @Column(name = "role", length = 100, nullable = false)
    private String role;

    @Column(name = "company", length = 120, nullable = false)
    private String company;

    @Column(name = "work_desc", length = 400, nullable = false)
    private String work_desc;

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
