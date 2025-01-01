package com.example.demo.Security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;



    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity (securedEnabled = true)

    public class Webconfig{

     /*   @Bean
        SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
            return http
            .cors(cors -> cors.configure(http))
            .csrf (csrf -> csrf.disable())
            .authorizeHttpRequests(authorrizeConfig -> 
             authorrizeConfig
             .requestMatchers(HttpMethod.POST, "equipamentos").permitAll()
             .requestMatchers(HttpMethod.GET, "equipamentos").permitAll()
             .requestMatchers(HttpMethod.PUT, "equipamentos").permitAll()
             .requestMatchers(HttpMethod.DELETE, "equipamentos").permitAll()
            ).build();

        } 
*/ 
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .cors(cors -> cors.disable())  // Desabilita a configuração CORS
        .csrf(csrf -> csrf.disable())  // Desabilita a proteção CSRF (não recomendado para produção)
        .authorizeHttpRequests(authorizeConfig ->
            authorizeConfig
                .anyRequest().permitAll()  // Permite todas as requisições (de qualquer método e URL)
        )
        .build();
}

    











}
