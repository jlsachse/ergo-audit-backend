package com.ergoton.ergoaudit.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final SignatureAuthenticationProvider signatureAuthenticationProvider;

     public SecurityConfig(SignatureAuthenticationProvider signatureAuthenticationProvider) {
        this.signatureAuthenticationProvider = signatureAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .and()
                .requestMatchers()
                    .antMatchers(HttpMethod.POST, "/reports/**", "/users/**")
                    .and()
                .addFilterBefore(
                        new SignatureAuthenticationFilter(authenticationManager()),
                        AnonymousAuthenticationFilter.class)
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);
        
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(signatureAuthenticationProvider));
    }





}
