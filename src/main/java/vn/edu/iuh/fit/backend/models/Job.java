package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "job")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Job {
    @Id
    @Column(name = "job_id", nullable = false, columnDefinition = "bigint(20)")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company", columnDefinition = "bigint(20)")
    @ToString.Exclude
    private Company company;

    @Column(name = "job_desc", columnDefinition = "varchar(2000)")
    private String jobDesc;

    @Column(name = "job_name", columnDefinition = "varchar(255)")
    private String jobName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return Objects.equals(id, job.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}