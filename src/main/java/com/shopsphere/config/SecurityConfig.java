
package com.shopsphere.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth

                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Login and registration
                .requestMatchers("/api/auth/**").permitAll()

                // Public categories
                .requestMatchers(
                    "/api/categories",
                    "/api/categories/**"
                ).permitAll()

                // Public product endpoints
                .requestMatchers(
                    "/api/products",
                    "/api/products/**"
                ).permitAll()

                // Orders
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/orders"
                ).permitAll()

                // Payments
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/payments/create-order",
                    "/api/payments/verify",
                    "/api/payments/demo"
                ).permitAll()

                .anyRequest().authenticated()
            );

        return http.build();
    }
}
