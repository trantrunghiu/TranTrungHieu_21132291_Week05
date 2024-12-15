package vn.edu.iuh.fit.backend.configs;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
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

    // Constructor khởi tạo service
    public SecurityConfig(CandidateService candidateService, CompanyService companyService) {
        this.candidateService = candidateService;
        this.companyService = companyService;
    }

    // Đăng ký thông tin OAuth2 cho Google
    @Bean
    public ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId("100255577152-ve0ub7qfg7uhsletvimgih80s81j0rsg.apps.googleusercontent.com")
                .clientSecret("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")
                .scope("profile", "email", "https://www.googleapis.com/auth/gmail.send")
                .authorizationUri("https://accounts.google.com/o/oauth2/auth")
                .tokenUri("https://oauth2.googleapis.com/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/google")
                .userNameAttributeName("sub")
                .build();
    }

    // Cung cấp dịch vụ OAuth2AuthorizedClientService cho việc quản lý client OAuth2 đã ủy quyền
    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(
            ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    // Cung cấp repository cho client OAuth2 đã ủy quyền
    @Bean
    public OAuth2AuthorizedClientRepository authorizedClientRepository(
            OAuth2AuthorizedClientService authorizedClientService) {
        return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);
    }

    // Lắng nghe sự kiện xác thực thành công và cập nhật quyền của người dùng (Candidate hoặc Company)
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

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2AuthToken = (OAuth2AuthenticationToken) authentication;
            SecurityContextHolder.getContext().setAuthentication(
                    new OAuth2AuthenticationToken(
                            oauth2AuthToken.getPrincipal(),
                            updatedAuthorities,
                            oauth2AuthToken.getAuthorizedClientRegistrationId()
                    )
            );
        }
    }

    // Cung cấp dịch vụ để xử lý thông tin người dùng từ Google OAuth2
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

    // Cung cấp sự kiện xác thực mặc định để phát hành các sự kiện khi có sự thay đổi về xác thực
    @Bean
    public DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher(ApplicationEventPublisher publisher) {
        return new DefaultAuthenticationEventPublisher(publisher);
    }

    // Cung cấp OAuth2AuthorizedClientManager để quản lý các client OAuth2 đã ủy quyền
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

    // Cấu hình bảo mật cho các yêu cầu HTTP và các trang cần bảo vệ
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
                        // Gửi phản hồi lỗi HTTP 403 hoặc chuyển hướng đến trang tùy chỉnh
                        response.sendRedirect("/");
                    });
                })
                .oauth2Login(httpSecurityOAuth2LoginConfigurer ->
                        httpSecurityOAuth2LoginConfigurer
                                .successHandler((request, response, authentication) -> {
                                    System.out.println("Authentication successful 181");
                                })
                                .defaultSuccessUrl("/", true)
                )
                .logout(logoutConfigurer ->
                        logoutConfigurer
                                .logoutUrl("/logout")  // URL đăng xuất
                                .logoutSuccessUrl("/") // URL khi đăng xuất thành công
                                .invalidateHttpSession(true)  // Xóa phiên làm việc khi đăng xuất
                                .clearAuthentication(true)  // Xóa thông tin xác thực
                )
                .addFilterBefore(new RedirectIfLoggedInFilter(candidateService, companyService), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


    // Cung cấp RestClient để thực hiện yêu cầu HTTP với các client OAuth2 đã ủy quyền
    @Bean
    public RestClient restClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        OAuth2ClientHttpRequestInterceptor requestInterceptor =
                new OAuth2ClientHttpRequestInterceptor(authorizedClientManager);
        return RestClient.builder()
                .requestInterceptor(requestInterceptor)
                .build();
    }

    // Cung cấp PasswordEncoder để mã hóa mật khẩu người dùng
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Cung cấp ClientRegistrationRepository cho các thông tin đăng ký client OAuth2
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
    }
}
