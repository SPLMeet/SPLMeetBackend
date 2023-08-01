package com.back.splitmeet.dto.user;

import com.back.splitmeet.service.user.KakaoApiClient;

public interface OAuthInfoResponse {
	String getEmail();

	String getNickname();

	KakaoApiClient.OAuthProvider getOAuthProvider();
	/*
	 *
	 * 필요 정보 추가 ex) kakao access token, refresh token, id token  ..etc
	 *
	 */
}
