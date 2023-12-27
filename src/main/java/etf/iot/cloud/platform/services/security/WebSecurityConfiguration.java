package etf.iot.cloud.platform.services.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Class that contains Spring security configuration
 */
@EnableWebSecurity   // includes our custom security configuration into spring security configuration
@Configuration       // marks our class as configuration class
public class WebSecurityConfiguration {
    /**
     * authorization filter that validates received requests
     */
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    /**
     * takes care of requests which failed authentication process
     */
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * Class constructor
     *
     * @param jwtAuthorizationFilter user defined jwt auth filter
     * @param authenticationEntryPoint  auth entry point
     */
    public WebSecurityConfiguration( JwtAuthorizationFilter jwtAuthorizationFilter, JwtAuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    /**
     * Bean responsible for handling auth process
     *
     * @param authenticationConfiguration config object
     * @return AuthenticationManager
     * @throws Exception
     */
    //interface declaring authenticate()  method, which accepts or rejects request depending on success of authentication
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /** Bean used for creating password's hash value
     *
     * @return password encoder object
     */

    // bean that provides password hashing and encoding
    @Bean
    public PasswordEncoder passwordEncoder() {      //service that provides password encryption and encoding
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuring session management, protected URLs, filters and exception handling
     *
     * @param http Spring security config object
     * @return spring security filter chain
     * @throws Exception
     */
    //declaring rules for accessing api
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .addFilter(corsFilter())
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(eh -> eh.authenticationEntryPoint(authenticationEntryPoint))
                .authorizeHttpRequests(ar -> ar.requestMatchers(HttpMethod.GET,"/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/signup").permitAll()
                        .requestMatchers(HttpMethod.GET,"/ws/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }


//    private HttpSecurity createAuthorizationRules(HttpSecurity http) throws Exception {
//        AuthorizationRules authorizationRules = new ObjectMapper().readValue(new ClassPathResource("rules.json").getInputStream(), AuthorizationRules.class);
//        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry =
//
//        for (Rule rule : authorizationRules.getRules()) {
//            if (rule.getMethods().isEmpty())
//                registry = registry.requestMatchers(rule.getPattern()).hasAnyAuthority(rule.getRoles().toArray(String[]::new));
//            else for (String method : rule.getMethods()) {
//                System.out.println("User with role " + rule.getRoles().get(0) + " can access " + rule.getPattern() + " with method " + rule.getMethods().get(0));
//                registry = registry.requestMatchers(HttpMethod.valueOf(method), rule.getPattern()).hasAnyAuthority(rule.getRoles().toArray(String[]::new));
//            }
//        }
//        return registry.anyRequest().authenticated().and();
//    }

    /**
     * Configuring cors filter for incoming requests
     *
     * @return CorsFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

}
