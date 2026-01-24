package com.mihirrueben.Recipe_app.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing with Postman
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/").permitAll() // Let anyone register/login
                        .requestMatchers("/api/users/").permitAll()
                        .requestMatchers("/api/recipes/**").permitAll()
                        .anyRequest().authenticated() // Lock everything else
                );
        return http.build();
    }
}