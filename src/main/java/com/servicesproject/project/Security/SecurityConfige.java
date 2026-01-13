package com.servicesproject.project.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableAspectJAutoProxy
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfige {

    @Bean
    BCryptPasswordEncoder  bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
@Bean
public   jwtFilter authf(){
        return new jwtFilter();
}

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()).headers(headers->headers.frameOptions(frame->frame.sameOrigin())).
                authorizeHttpRequests(auth -> auth
                        .requestMatchers("/Auth/Login" , "/Auth/register","/h2-console/**").permitAll().requestMatchers("/ws/**").permitAll()
                        .anyRequest().authenticated()
                ).addFilterBefore(authf(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
