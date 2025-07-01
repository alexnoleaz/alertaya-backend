package com.alertaya.backend.auth.jwtbearer;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService service;

	public JwtAuthenticationFilter(JwtService service) {
		this.service = service;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (isPublicEndpoint(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		var token = resolveToken(request);
		if (token == null) {
			request.setAttribute("authException", "Token is missing or invalid.");
			filterChain.doFilter(request, response);
			return;
		}

		try {
			var claims = service.parse(token);
			var userId = claims.getSubject();
			var authorities = ((List<?>) claims.get("roles", List.class)).stream()
					.map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString())).toList();

			var authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (ExpiredJwtException ex) {
			request.setAttribute("authException", "Expired token.");
		} catch (MalformedJwtException | SignatureException | IllegalArgumentException ex) {
			request.setAttribute("authException", "Invalid token.");
		}

		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		final var header = request.getHeader("Authorization");
		return (header != null && header.startsWith("Bearer ")) ? header.substring(7) : null;
	}

	private boolean isPublicEndpoint(HttpServletRequest request) {
		return request.getServletPath().startsWith("/api/auth");
	}
}
