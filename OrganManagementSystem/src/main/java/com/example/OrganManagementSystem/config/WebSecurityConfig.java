package com.example.OrganManagementSystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(jwtUserDetailsService);
        authenticationProvider.setPasswordEncoder(bcryptEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((configurer) -> {
                    configurer.requestMatchers("/authenticate", "/register_user").permitAll()
                            // working, needed
                            .requestMatchers(HttpMethod.POST, "/register_doctor", "/register_admin").hasRole("ADMIN")
                            // working, needed
                            .requestMatchers(HttpMethod.GET, "/admin/doctors").hasRole("ADMIN")
                            // working, needed
                            .requestMatchers(HttpMethod.GET, "/admin/patients").hasRole("ADMIN")
                            // working, needed
                            .requestMatchers(HttpMethod.GET, "/admin/doctors/{doctor_id}").hasRole("ADMIN")
                            // working, needed
                            .requestMatchers(HttpMethod.GET, "/admin/patients/{patientId}").hasRole("ADMIN")
                            // working, needed
                            .requestMatchers(HttpMethod.POST, "/user/addPatientInfo").hasRole("USER")
                            // working, needed
                            .requestMatchers(HttpMethod.PUT, "/user/updateMyInfo").hasRole("USER")
                            // working, needed
                            .requestMatchers(HttpMethod.GET, "/user/viewMyInfo").hasRole("USER")
                            // working, needed
                            .requestMatchers(HttpMethod.GET, "/doctor/viewPatients/**", "/doctor/viewPatients").hasRole("DOCTOR")
                            // working, needed
                            .requestMatchers(HttpMethod.PUT, "/doctor/updateMyInfo").hasRole("DOCTOR")
                            // working, needed
                            .requestMatchers(HttpMethod.GET, "/doctor/viewMyInfo").hasRole("DOCTOR")
                            // working, needed
                            .requestMatchers(HttpMethod.POST, "/doctor/addMyInfo").hasRole("DOCTOR")
                            .anyRequest().authenticated();
                }).exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CorsFilterConfig(), ChannelProcessingFilter.class);

        return http.build();
    }
}
