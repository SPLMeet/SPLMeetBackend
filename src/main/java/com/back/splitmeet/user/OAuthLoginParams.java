package com.back.splitmeet.user;

import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    KakaoApiClient.OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
