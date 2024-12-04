package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.backend.ids.CandidateSkillId;
import vn.edu.iuh.fit.backend.models.CandidateSkill;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, CandidateSkillId> {
}