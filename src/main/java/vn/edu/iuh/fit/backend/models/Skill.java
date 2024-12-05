package vn.edu.iuh.fit.backend.models;

import java.util.Objects;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skill")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Skill {
    @Id
    @Column(name = "skill_id", nullable = false, columnDefinition = "bigint(20)")
    private Long id;

    @Column(name = "skill_type", columnDefinition = "tinyint(4)")
    private Byte skillType;

    @Column(name = "skill_name", columnDefinition = "varchar(150)")
    private String skillName;

    @Column(name = "skil_desc", columnDefinition = "varchar(300)")
    private String skillDesc;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(id, skill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}