package com.mdv.appstore.config;

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
        private final JwtAuthFilter jwtAuthFilter;

        @Value("${app.api.base-url}")
        private String apiBaseUrl;

        public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
                this.jwtAuthFilter = jwtAuthFilter;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration authenticationConfiguration) throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(AbstractHttpConfigurer::disable)
                                .cors(Customizer.withDefaults())
                                .sessionManagement(
                                                sessionManagement -> sessionManagement.sessionCreationPolicy(
                                                                SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(
                                                authorizeRequests -> authorizeRequests
                                                                .requestMatchers(
                                                                                apiBaseUrl + "/auth/login",
                                                                                apiBaseUrl + "/auth/register",
                                                                                apiBaseUrl + "/auth/refresh-token")
                                                                .permitAll()
                                                                .requestMatchers(
                                                                                HttpMethod.GET,
                                                                                apiBaseUrl + "/products",
                                                                                apiBaseUrl + "/products/{id}",
                                                                                apiBaseUrl + "/products/search",
                                                                                apiBaseUrl + "/products/category/{categoryId}",
                                                                                apiBaseUrl + "/products/brand/{brandId}",
                                                                                apiBaseUrl + "/products/{id}/reviews")
                                                                .permitAll()
                                                                .requestMatchers(
                                                                                HttpMethod.GET,
                                                                                apiBaseUrl + "/categories",
                                                                                apiBaseUrl + "/categories/{id}",
                                                                                apiBaseUrl + "/categories/{id}/products")
                                                                .permitAll()
                                                                .requestMatchers(
                                                                                HttpMethod.GET,
                                                                                apiBaseUrl + "/brands",
                                                                                apiBaseUrl + "/brands/{id}",
                                                                                apiBaseUrl + "/brands/{id}/products")
                                                                .permitAll()
                                                                .requestMatchers(
                                                                                HttpMethod.GET,
                                                                                apiBaseUrl + "/promotions",
                                                                                apiBaseUrl + "/promotions/active")
                                                                .permitAll()
                                                                .requestMatchers(
                                                                                HttpMethod.GET,
                                                                                apiBaseUrl + "/reviews/product/{productId}")
                                                                .permitAll()
                                                                .requestMatchers(
                                                                                HttpMethod.GET,
                                                                                apiBaseUrl + "/vouchers/public",
                                                                                apiBaseUrl + "/vouchers/validate/{code}")
                                                                .permitAll()
                                                                .requestMatchers(apiBaseUrl + "/revenue/total")
                                                                .hasAnyAuthority("ADMIN", "EMPLOYEE")

                                                                // .requestMatchers("/v3/api-docs/**",
                                                                // "/swagger-ui/**",
                                                                // "/swagger-ui.html")
                                                                // .permitAll()

                                                                .requestMatchers(
                                                                                apiBaseUrl + "/**",
                                                                                apiBaseUrl + "/auth/logout")
                                                                .authenticated())
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                                .exceptionHandling(
                                                exceptionHandling -> exceptionHandling.authenticationEntryPoint(
                                                                new JwtAuthEntryPoint()));

                return http.build();
        }
}
