package vn.edu.iuh.fit.frontend.restapis;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.backend.models.Skill;
import vn.edu.iuh.fit.backend.services.SkillService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/skills")  // Đường dẫn API để lấy danh sách kỹ năng
public class SkillApiController {

    @Autowired
    private SkillService skillService;

    public SkillApiController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public ResponseEntity<?> getSkills(@PathParam("name") String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            List<Skill> skills = skillService.findByName(name);
            Map<String, Object> response = new HashMap<>();
            response.put("skills", skills);
            return ResponseEntity.ok(response);

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
