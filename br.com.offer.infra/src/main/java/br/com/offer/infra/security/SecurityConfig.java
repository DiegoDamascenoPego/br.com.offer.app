package br.com.offer.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(
                auth -> {
                    auth.requestMatchers(
                        "/public/**",
                        "/actuator/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html").permitAll();
                    auth.anyRequest().authenticated();
                }
            )
            .oauth2Login(conf -> {
                conf.defaultSuccessUrl("/api/", false);
                conf.failureUrl("/login?error");
            })
            .oauth2ResourceServer(conf -> conf.jwt(Customizer.withDefaults()))
            .csrf(Customizer.withDefaults())
            .build();
    }

}
