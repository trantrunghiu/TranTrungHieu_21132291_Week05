package vn.edu.iuh.fit.backend.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestClient;
import vn.edu.iuh.fit.backend.filters.RedirectIfLoggedInFilter;
import vn.edu.iuh.fit.backend.models.Candidate;
import vn.edu.iuh.fit.backend.models.Company;
import vn.edu.iuh.fit.backend.services.AddressService;
import vn.edu.iuh.fit.backend.services.CandidateService;
import vn.edu.iuh.fit.backend.services.CompanyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CandidateService candidateService;
    private final CompanyService companyService;

    public SecurityConfig(CandidateService candidateService, CompanyService companyService) {
        this.candidateService = candidateService;
        this.companyService = companyService;
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String generatedPassword = passwordEncoder.encode("user");
        return new InMemoryUserDetailsManager(
                User.withUsername("user")
                        .password(generatedPassword)
                        .roles("USER")
                        .build());
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @EventListener
    public void handleAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        Optional<Candidate> candidate = candidateService.findByEmail(principal.getAttribute("email"));
        Optional<Company> company = companyService.findByEmail(principal.getAttribute("email"));

        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(authentication.getAuthorities());
        if (candidate.isPresent()) {
            updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_CANDIDATE"));
        } else if (company.isPresent()) {
            updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_COMPANY"));
        }

        if (authentication instanceof OAuth2AuthenticationToken oauth2AuthToken) {
            SecurityContextHolder.getContext().setAuthentication(
                    new OAuth2AuthenticationToken(
                            oauth2AuthToken.getPrincipal(),
                            updatedAuthorities,
                            oauth2AuthToken.getAuthorizedClientRegistrationId()
                    )
            );
        }
    }


    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return userRequest -> {
            OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
            String email = oAuth2User.getAttribute("email");
            List<GrantedAuthority> authorities = new ArrayList<>(oAuth2User.getAuthorities());

            if (candidateService.findByEmail(email).isPresent()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_CANDIDATE"));
            } else if (companyService.findByEmail(email).isPresent()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_COMPANY"));
            }

            return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "email");
        };
    }

    @Bean
    public DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher(ApplicationEventPublisher publisher) {
        return new DefaultAuthenticationEventPublisher(publisher);
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()
                        .refreshToken()
                        .clientCredentials()
                        .provider(new JwtBearerOAuth2AuthorizedClientProvider())
                        .build();

        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
                new DefaultOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CandidateService candidateService, AddressService addressService, CompanyService companyService) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/oauth2/**").permitAll()
                                .requestMatchers("/candidate", "/candidate/**").hasAuthority("ROLE_CANDIDATE")
                                .requestMatchers("/company", "/company/**").hasAuthority("ROLE_COMPANY")
                                .requestMatchers("/skills/**").hasAnyAuthority("ROLE_COMPANY", "ROLE_CANDIDATE")
                                .requestMatchers("/list", "/candidates").permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
                    httpSecurityExceptionHandlingConfigurer.accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.sendRedirect("/");
                    });
                })
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl("/logout") // Endpoint để logout
                        .logoutSuccessUrl("/") // URL chuyển hướng sau khi logout
                        .invalidateHttpSession(true) // Xóa session
                        .clearAuthentication(true) // Xóa xác thực
                        .addLogoutHandler((request, response, authentication) -> {
                            System.out.println("User logged out: " + (authentication != null ? authentication.getName() : "Unknown"));
                        })
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("/"); // Chuyển hướng đến trang chủ
                        })
                )
                .oauth2Login(httpSecurityOAuth2LoginConfigurer ->
                        httpSecurityOAuth2LoginConfigurer
                                .successHandler((request, response, authentication) -> {
                                    System.out.println("Authentication successful 181");
                                })
                                .defaultSuccessUrl("/", true)
                )
                .addFilterBefore(new RedirectIfLoggedInFilter(candidateService, companyService), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public RestClient restClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        OAuth2ClientHttpRequestInterceptor requestInterceptor =
                new OAuth2ClientHttpRequestInterceptor(authorizedClientManager);
        return RestClient.builder()
                .requestInterceptor(requestInterceptor)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
