package com.example.demo.Security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;



    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity (securedEnabled = true)

    public class Webconfig{

        @Bean
        SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
            return http
            .cors(cors -> cors.configure(http))
            .csrf (csrf -> csrf.disable())
            .authorizeHttpRequests(authorrizeConfig -> 
             authorrizeConfig
             .requestMatchers(HttpMethod.POST, "Vinculo").permitAll()
            ).build();

        } 



    











}
