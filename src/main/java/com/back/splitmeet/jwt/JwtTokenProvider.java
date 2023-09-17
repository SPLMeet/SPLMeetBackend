package com.back.splitmeet.jwt;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.back.splitmeet.domain.UserInfo;
import com.back.splitmeet.domain.repository.UserInfoRepository;
import com.back.splitmeet.jwt.dto.TokenInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Getter
public class JwtTokenProvider {

	private final long JWT_ACCESS_TOKEN_EXPTIME;
	private final long JWT_REFRESH_TOKEN_EXPTIME;
	private final String JWT_SECRET_KEY;
	private Key jwtKey;

	private final UserInfoRepository userInfoRepository;
	private final UserDetailsService userDetailsService;

	@Autowired
	public JwtTokenProvider(
		@Value("${jwt.time.access}") long JWT_ACCESS_TOKEN_EXPTIME,
		@Value("${jwt.time.refresh}") long JWT_REFRESH_TOKEN_EXPTIME,
		@Value("${jwt.secret}") String JWT_SECRET_KEY
		, UserInfoRepository userInfoRepository, UserDetailsService userDetailsService) {
		this.JWT_ACCESS_TOKEN_EXPTIME = JWT_ACCESS_TOKEN_EXPTIME;
		this.JWT_REFRESH_TOKEN_EXPTIME = JWT_REFRESH_TOKEN_EXPTIME;
		this.JWT_SECRET_KEY = JWT_SECRET_KEY;
		this.userInfoRepository = userInfoRepository;
		this.userDetailsService = userDetailsService;
	}

	@PostConstruct
	public void initialize() {
		byte[] accessKeyBytes = Decoders.BASE64.decode(JWT_SECRET_KEY);
		this.jwtKey = Keys.hmacShaKeyFor(accessKeyBytes);
	}

	public String createAccessToken(Long userId, Long teamId, Integer role, String useremail, String name,
		String picture) {
		Header headers = Jwts.header().setContentType("jwt");
		Claims claims = Jwts.claims();
		claims.put("userId", userId);
		claims.put("teamId", teamId);
		claims.put("role", role);
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

	public String createRefreshToken(Long userId, String useremail, String name) {
		Header headers = Jwts.header().setContentType("jwt");
		Claims claims = Jwts.claims().setSubject(userId.toString());
		claims.put("useremail", useremail);
		claims.put("name", name);

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

	public String getUserEmail(String token) {
		return Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (ExpiredJwtException e) {
			log.info(e.getMessage());
			return false;
		}
	}

	public TokenInfo getUserInfoFromAcs(String token) {
		return new TokenInfo(((Number)Jwts.parserBuilder().setSigningKey(jwtKey).build()
			.parseClaimsJws(token).getBody().get("userId")).longValue(),
			(String)Jwts.parserBuilder().setSigningKey(jwtKey).build()
				.parseClaimsJws(token).getBody().get("email"),
			(String)Jwts.parserBuilder().setSigningKey(jwtKey).build()
				.parseClaimsJws(token).getBody().get("name"));
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(token);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public UserInfo getRoles(String email) {
		return userInfoRepository.findByUserEmail(email);
	}

	public String resolveAccessToken(HttpServletRequest request) {
		if (request.getHeader("authorization") != null)
			return request.getHeader("authorization").substring(7);
		return null;
	}

	public String resolveRefreshToken(HttpServletRequest request) {
		if (request.getHeader("refreshToken") != null)
			return request.getHeader("refreshToken").substring(7);
		return null;
	}

	public boolean existsRefreshToken(String refreshToken) {
		return userInfoRepository.existsByRefreshToken(refreshToken);
	}
}
