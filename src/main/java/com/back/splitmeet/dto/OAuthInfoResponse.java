package com.back.splitmeet.dto;

import com.back.splitmeet.user.KakaoApiClient;

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
