package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.iuh.fit.backend.ids.JobSkillId;

import java.util.Objects;

@Entity
@Table(name = "job_skill")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobSkill {
    @EmbeddedId
    private JobSkillId id;

    @Column(name = "skill_level", columnDefinition = "tinyint(4)")
    private Byte skillLevel;

    @Column(name = "more_infos", columnDefinition = "varchar(1000)")
    private String moreInfos;

    @MapsId("jobId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_id", nullable = false, columnDefinition = "bigint(20)")
    private Job job;

    @MapsId("skillId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skill_id", nullable = false, columnDefinition = "bigint(20)")
    private Skill skill;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobSkill jobSkill = (JobSkill) o;
        return Objects.equals(id, jobSkill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}