package com.back.splitmeet.src.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.src.auth.dto.ChkUserInfoRes;
import com.back.splitmeet.util.BaseResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
	private AuthService authService;

	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@GetMapping
	public BaseResponse<ChkUserInfoRes> doAuth(@RequestHeader(value = "Authorization") String accessToken) {

		return new BaseResponse<>(authService.getUserInfo(accessToken));
	}
}
