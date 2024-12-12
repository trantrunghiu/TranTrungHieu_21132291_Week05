package vn.edu.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.iuh.fit.backend.models.Company;
import vn.edu.iuh.fit.backend.models.Job;
import vn.edu.iuh.fit.backend.repositories.CompanyRepository;
import vn.edu.iuh.fit.backend.repositories.JobRepository;

import java.sql.Date;

@Controller
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping("/saveJob")
    public String saveJob(@RequestParam("companyId") Long companyId,
                          @RequestParam("jobTitle") String jobTitle,
                          @RequestParam("jobDescription") String jobDescription,
                          @RequestParam("postedDate") String postedDate,
                          @RequestParam("expiryDate") String expiryDate,
                          @RequestParam("salaryRange") String salaryRange,
                          @RequestParam("experienceRequired") String experienceRequired,
                          @RequestParam("location") String location, Model model) {

        Company company = companyRepository.findById(companyId).orElseThrow(() -> new RuntimeException("Company not found"));


        Job job = new Job();
        job.setCompany(company);
        job.setJobName(jobTitle);
        job.setJobDesc(jobDescription);

        jobRepository.save(job);

        // Thêm thông báo thành công vào model
        model.addAttribute("message", "Job has been saved successfully!");

        return "index";

    }
}
