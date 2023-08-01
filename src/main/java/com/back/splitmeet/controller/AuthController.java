package com.back.splitmeet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.service.user.OAuthLoginService;
import com.back.splitmeet.user.KakaoLoginParams;
import com.back.splitmeet.user.token.AuthTokens;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
	private final OAuthLoginService oAuthLoginService;

	@PostMapping("/kakao")
	public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params) {
		return ResponseEntity.ok(oAuthLoginService.login(params));
	}
}
