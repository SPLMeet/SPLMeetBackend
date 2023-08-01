package com.back.splitmeet.dto.user;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.back.splitmeet.service.user.KakaoApiClient;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoLoginParams implements OAuthLoginParams {
	private String authorizationCode;

	@Override
	public KakaoApiClient.OAuthProvider oAuthProvider() {
		return KakaoApiClient.OAuthProvider.KAKAO;
	}

	@Override
	public MultiValueMap<String, String> makeBody() {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("code", authorizationCode);
		return body;
	}
}
