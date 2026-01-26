package com.mihirrueben.Recipe_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF - Required for stateless APIs
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Enable CORS with the configuration defined below
                .cors(Customizer.withDefaults())

                // 3. Set Session Management to Stateless (No cookies)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 4. Define Authorization Rules
                .authorizeHttpRequests(auth -> auth
                        // Allow anyone to view recipes, user profiles, and images
                        .requestMatchers(HttpMethod.GET, "/api/recipes/**", "/api/users/**", "/uploads/**").permitAll()

                        // Allow anyone to register or login
                        .requestMatchers("/api/auth/**").permitAll()
                        // Allow anyone to upload image (temporary)
                        .requestMatchers("/api/images/**").permitAll()

                        // Require authentication for modifying data (POST, PUT, DELETE)
                        .requestMatchers(HttpMethod.POST, "/api/recipes/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/recipes/**", "/api/users/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/recipes/**").authenticated()

                        // Any other request (like image uploads) must be authenticated
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 5. CORS Configuration to tell the browser which frontends calls to this API
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow frontend origins (Commonly 3000 for React, 5173 for Vite)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5173"));

        // Allowed HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allowed Headers (Content-Type and Authorization are essential)
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));

        // Allow sending cookies/auth headers
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}