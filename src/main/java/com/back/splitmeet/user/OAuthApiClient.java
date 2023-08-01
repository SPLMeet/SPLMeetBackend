package com.back.splitmeet.user;

import com.back.splitmeet.user.dto.OAuthInfoResponse;

public interface OAuthApiClient {
    KakaoApiClient.OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
