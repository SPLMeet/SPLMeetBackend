package com.back.splitmeet.dto.user;

import org.springframework.util.MultiValueMap;

import com.back.splitmeet.service.user.KakaoApiClient;

public interface OAuthLoginParams {
	KakaoApiClient.OAuthProvider oAuthProvider();

	MultiValueMap<String, String> makeBody();
}
