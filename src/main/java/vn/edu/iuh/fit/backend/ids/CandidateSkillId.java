package vn.edu.iuh.fit.backend.ids;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CandidateSkillId implements Serializable {
    @Column(name = "can_id", nullable = false)
    private Long canId;

    @Column(name = "skill_id", nullable = false)
    private Long skillId;
}