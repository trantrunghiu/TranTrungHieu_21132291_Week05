package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.iuh.fit.backend.ids.CandidateSkillId;

import java.util.Objects;

@Entity
@Table(name = "candidate_skill")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateSkill {
    @EmbeddedId
    private CandidateSkillId id;

    @Column(name = "skill_level", columnDefinition = "tinyint(4)")
    private Byte skillLevel;

    @Column(name = "more_infos", columnDefinition = "varchar(1000)")
    private String moreInfos;

    @MapsId("canId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "can_id", nullable = false, columnDefinition = "bigint(20)")
    private Candidate can;

    @MapsId("skillId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skill_id", nullable = false, columnDefinition = "bigint(20)")
    private Skill skill;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandidateSkill that = (CandidateSkill) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}