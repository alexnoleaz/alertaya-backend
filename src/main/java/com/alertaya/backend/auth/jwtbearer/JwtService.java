package com.alertaya.backend.auth.jwtbearer;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	private final JwtConfiguration configuration;
	private final SecretKey key;

	public JwtService(JwtConfiguration configuration) {
		this.configuration = configuration;
		this.key = Keys.hmacShaKeyFor(configuration.getSecurityKey().getBytes(StandardCharsets.UTF_8));
	}

	public Claims parse(String token) {
		return Jwts.parser()
				.verifyWith(key)
				.requireIssuer(configuration.getIssuer())
				.requireAudience(configuration.getAudience())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public String generate(Long userId, String[] roles) {
		var now = new Date();
		return Jwts.builder()
				.issuer(configuration.getIssuer())
				.audience().add(configuration.getAudience()).and()
				.claims(createJwtClaims(userId, roles))
				.notBefore(now)
				.expiration(new Date(now.getTime() + (Duration.ofDays(1).toMillis())))
				.signWith(key)
				.compact();
	}

	private Claims createJwtClaims(Long userId, String[] roles) {
		var claims = Jwts.claims().subject(userId.toString())
				.id(UUID.randomUUID().toString())
				.issuedAt(new Date());

		if (roles != null && roles.length > 0)
			claims.add("roles", List.of(roles));

		return claims.build();
	}
}
