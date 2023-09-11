package com.back.splitmeet.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.back.splitmeet.domain.UserInfo;
import com.back.splitmeet.jwt.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String accessToken = jwtTokenProvider.resolveAccessToken(request);
		String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

		if (accessToken != null) {
			if (jwtTokenProvider.validateToken(accessToken)) {
				this.setAuthentication(accessToken);
			} else if (!jwtTokenProvider.validateToken(accessToken) && refreshToken != null) {
				boolean validateRefreshToken = jwtTokenProvider.validateToken(refreshToken);
				boolean isRefreshToken = jwtTokenProvider.existsRefreshToken(refreshToken);
				if (validateRefreshToken && isRefreshToken) {
					String email = jwtTokenProvider.getUserEmail(refreshToken);
					UserInfo userinfo = jwtTokenProvider.getRoles(email);
					String newAccessToken = jwtTokenProvider.createAccessToken(userinfo.getUserId(),
						userinfo.getTeamId(), userinfo.getRole(), userinfo.getUserEmail(), userinfo.getUserName(),
						userinfo.getUserProfile());
					response.setHeader("Authorization", accessToken);
					this.setAuthentication(newAccessToken);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	public void setAuthentication(String accessToken) {
		// 토큰으로부터 유저 정보를 받아옵니다.
		Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
