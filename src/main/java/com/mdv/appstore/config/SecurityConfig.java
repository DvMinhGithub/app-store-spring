package com.mdv.appstore.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mdv.appstore.security.jwt.JwtAuthEntryPoint;
import com.mdv.appstore.security.jwt.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    private static final String[] PUBLIC_GET_ENDPOINTS = {
        "/products/**",
        "/categories/**",
        "/brands/**",
        "/promotions/**",
        "/reviews/product/{productId}",
        "/vouchers/public",
        "/vouchers/validate/{code}"
    };

    private static final String[] PUBLIC_POST_ENDPOINTS = {"/auth/**"};

    private static final String[] ADMIN_GET_ENDPOINTS = {"/revenue/**"};

    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    @Value("${app.api.base-url}")
    private String apiBaseUrl;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, JwtAuthEntryPoint jwtAuthEntryPoint) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private String[] getPublicGetEndpoints(String[] endpoints) {
        return Arrays.stream(endpoints).map(endpoint -> apiBaseUrl + endpoint).toArray(String[]::new);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] publicGetEndpoints = getPublicGetEndpoints(PUBLIC_GET_ENDPOINTS);

        String[] publicPostEndpoints = getPublicGetEndpoints(PUBLIC_POST_ENDPOINTS);

        String[] adminGetEndpoints = getPublicGetEndpoints(ADMIN_GET_ENDPOINTS);

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET, publicGetEndpoints)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/image/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, publicPostEndpoints)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, adminGetEndpoints)
                        .hasAnyAuthority("ADMIN", "EMPLOYEE")
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint));

        return http.build();
    }
}
