package com.alertaya.backend.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alertaya.backend.auth.jwtbearer.JwtAuthenticationFilter;
import com.alertaya.backend.shared.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableMethodSecurity
public class ApplicationSecurityConfig {
	private final JwtAuthenticationFilter authenticationFilter;
	private final ObjectMapper mapper;

	public ApplicationSecurityConfig(JwtAuthenticationFilter authenticationFilter, ObjectMapper mapper) {
		this.authenticationFilter = authenticationFilter;
		this.mapper = mapper;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf(cfg -> cfg.disable())
				.authorizeHttpRequests(req -> req
						.requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
						.anyRequest().authenticated())
				.exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationEntryPoint authenticationEntryPoint() {
		return (request, response, authException) -> {
			var message = (String) request.getAttribute("authException");
			if (message != null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("application/json");
				response.getWriter().write(mapper.writeValueAsString(
						Response.error(message, HttpStatus.UNAUTHORIZED)));
			}
		};
	}
}
