package com.back.splitmeet.service.user;

import com.back.splitmeet.dto.user.OAuthInfoResponse;
import com.back.splitmeet.dto.user.OAuthLoginParams;

public interface OAuthApiClient {
	KakaoApiClient.OAuthProvider oAuthProvider();

	String requestAccessToken(OAuthLoginParams params);

	OAuthInfoResponse requestOauthInfo(String accessToken);
}
