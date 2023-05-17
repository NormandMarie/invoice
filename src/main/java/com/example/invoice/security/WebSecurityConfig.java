package com.example.invoice.security;

import com.example.invoice.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    UserDetailsServiceImpl customUserDetailsService;

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailsService);
        return provider;
    }

    private static final String[] WHITELIST_RESSOURCES = {"/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico"};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(WHITELIST_RESSOURCES).permitAll();
                })
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers("/").permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin(form -> {
                    form
                            .loginPage("/login")               // Indique que je souhaite utilser mon propre thymeleaf
                            .usernameParameter("email")       // <input name="email">
                            .passwordParameter("password")    // <input name="password">
                            .permitAll()
                            .defaultSuccessUrl("/home");
                })
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                })
                .build();
    }
}