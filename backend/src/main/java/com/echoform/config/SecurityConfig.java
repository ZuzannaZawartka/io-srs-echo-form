package com.echoform.config;

import com.echoform.security.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final RestAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> {}) 
            .csrf(AbstractHttpConfigurer::disable)
            
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/login/ott").permitAll()
                .requestMatchers("/api/forms/**").permitAll()
                .requestMatchers("/api-docs/**", "/swagger-ui/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .anyRequest().authenticated()
            )
            
            .exceptionHandling(handling -> handling
                .authenticationEntryPoint(authenticationEntryPoint)
            )
            
            // Handle POST /api/login/ott: Validate token, Create Session
            .oneTimeTokenLogin(ott -> ott
                .loginProcessingUrl("/api/login/ott")
                .showDefaultSubmitPage(false)
                .authenticationSuccessHandler(ottLoginSuccessHandler())
                .authenticationFailureHandler(ottLoginFailureHandler())
            );

        return http.build();
    }


    @Bean
    public org.springframework.security.authentication.ott.OneTimeTokenService oneTimeTokenService(javax.sql.DataSource dataSource) {
        // JdbcOneTimeTokenService stores tokens in the DB (H2)
        return new org.springframework.security.authentication.ott.JdbcOneTimeTokenService(new org.springframework.jdbc.core.JdbcTemplate(dataSource));
    }

    @Bean
    public org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler oneTimeTokenGenerationSuccessHandler() {
        return (request, response, oneTimeToken) -> {
            response.setStatus(200);
        };
    }

    @Bean
    public org.springframework.security.web.authentication.AuthenticationSuccessHandler ottLoginSuccessHandler() {
        return (request, response, authentication) -> {
            response.setStatus(200);
        };
    }

    @Bean
    public org.springframework.security.web.authentication.AuthenticationFailureHandler ottLoginFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Authentication Failed\", \"message\": \"" + exception.getMessage() + "\"}");
        };
    }

}
