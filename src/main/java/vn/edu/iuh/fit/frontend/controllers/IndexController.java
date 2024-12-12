package vn.edu.iuh.fit.frontend.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.iuh.fit.backend.services.CandidateService;
import vn.edu.iuh.fit.backend.services.CompanyService;

@Controller
@RequestMapping("/")
public class IndexController {
    private final CandidateService candidateService;
    private final CompanyService companyService;

    public IndexController(CandidateService candidateService, CompanyService companyService) {
        this.candidateService = candidateService;
        this.companyService = companyService;
    }
    @GetMapping
    public String index(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "index";

    }
}
