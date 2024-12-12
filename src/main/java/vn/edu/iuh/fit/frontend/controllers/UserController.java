package vn.edu.iuh.fit.frontend.controllers;

import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
public class UserController {
//    private final AuthenticationManager authenticationManager;
//
//    public LoginController(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }



    //    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest loginRequest) {
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password);
//        Authentication authentication = authenticationManager.authenticate(authenticationToken);
//        if (authentication != null) {
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            return "redirect:/";
//        }
//        return "redirect:/login?error=true";
//    }
    private record LoginRequest(String username, String password) {}
    private record RegisterCandidateRequest(String username, LocalDate dob, String email, String password) {}
}
