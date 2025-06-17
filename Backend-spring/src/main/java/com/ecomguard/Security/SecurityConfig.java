package com.ecomguard.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(
                "/api/reviews/**",           // allow review detection
                "/api/return-abuse/**"       // allow return abuse detection
            ).permitAll()
            .anyRequest().permitAll()
            .and()
            .httpBasic(); // for local testing only
        return http.build();
    }
}
