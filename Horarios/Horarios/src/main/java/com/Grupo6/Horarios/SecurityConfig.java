package com.Grupo6.Horarios;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Desactiva CSRF (opcional para pruebas locales)
                .authorizeRequests()
                .anyRequest().permitAll(); // Permite todas las solicitudes sin autenticación

        return http.build();
    }
}
