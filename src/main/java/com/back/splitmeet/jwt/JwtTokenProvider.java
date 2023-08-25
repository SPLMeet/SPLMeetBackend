package com.back.splitmeet.jwt;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Component
@Getter
public class JwtTokenProvider {

	private final long JWT_ACCESS_TOKEN_EXPTIME;
	private final long JWT_REFRESH_TOKEN_EXPTIME;
	private final String JWT_SECRET_KEY;
	private Key jwtKey;

	@Autowired
	public JwtTokenProvider(
		@Value("${jwt.time.access}") long JWT_ACCESS_TOKEN_EXPTIME,
		@Value("${jwt.time.refresh}") long JWT_REFRESH_TOKEN_EXPTIME,
		@Value("${jwt.secret}") String JWT_SECRET_KEY) {
		this.JWT_ACCESS_TOKEN_EXPTIME = JWT_ACCESS_TOKEN_EXPTIME;
		this.JWT_REFRESH_TOKEN_EXPTIME = JWT_REFRESH_TOKEN_EXPTIME;
		this.JWT_SECRET_KEY = JWT_SECRET_KEY;
	}

	@PostConstruct
	public void initialize() {
		byte[] accessKeyBytes = Decoders.BASE64.decode(JWT_SECRET_KEY);
		this.jwtKey = Keys.hmacShaKeyFor(accessKeyBytes);
	}

	public String createAccessToken(String useremail, String name, String picture) {
		Header headers = Jwts.header().setContentType("jwt");
		Claims claims = Jwts.claims();
		claims.put("email", useremail);
		claims.put("name", name);
		claims.put("picture", picture);
		Date now = Date.from(ZonedDateTime.now().toInstant());
		return Jwts.builder()
			.setHeader((Map<String, Object>)headers)
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(
				Date.from(ZonedDateTime.now().plusSeconds(JWT_ACCESS_TOKEN_EXPTIME).toInstant()))
			.signWith(jwtKey, SignatureAlgorithm.HS512)
			.compact();
	}

	public String createRefreshToken(String useremail) {
		Header headers = Jwts.header().setContentType("jwt");
		Claims claims = Jwts.claims().setSubject(useremail);

		Date now = Date.from(ZonedDateTime.now().toInstant());
		return Jwts.builder()
			.setHeader((Map<String, Object>)headers)
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(
				Date.from(ZonedDateTime.now().plusSeconds(JWT_REFRESH_TOKEN_EXPTIME).toInstant()))
			.signWith(jwtKey, SignatureAlgorithm.HS512)
			.compact();
	}

	public String getUseridFromAcs(String token) {
		return Jwts.parserBuilder().setSigningKey(jwtKey).build()
			.parseClaimsJws(token).getBody().getSubject();
	}

	public String getUseridFromRef(String token) {
		return Jwts.parserBuilder().setSigningKey(jwtKey).build()
			.parseClaimsJws(token).getBody().getSubject();
	}

	public Claims getJwtContents(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).getBody();

		return claims;
	}

	public String getEmail(String token) {
		Claims claims = getJwtContents(token);
		return claims.get("email").toString();
	}

	public Long getExpiration(String accessToken) {
		Date expiration = Jwts.parserBuilder()
			.setSigningKey(jwtKey)
			.build()
			.parseClaimsJws(accessToken)
			.getBody()
			.getExpiration();
		long now = Date.from(ZonedDateTime.now().toInstant()).getTime();
		return expiration.getTime() - now;
	}
}
