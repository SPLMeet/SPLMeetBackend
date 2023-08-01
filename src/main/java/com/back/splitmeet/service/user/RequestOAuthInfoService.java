package com.back.splitmeet.service.user;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.back.splitmeet.dto.user.OAuthInfoResponse;
import com.back.splitmeet.dto.user.OAuthLoginParams;

@Component
public class RequestOAuthInfoService {
	private final Map<KakaoApiClient.OAuthProvider, OAuthApiClient> clients;

	public RequestOAuthInfoService(List<OAuthApiClient> clients) {
		this.clients = clients.stream().collect(
			Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())
		);
	}

	public OAuthInfoResponse request(OAuthLoginParams params) {
		OAuthApiClient client = clients.get(params.oAuthProvider());
		String accessToken = client.requestAccessToken(params);
		return client.requestOauthInfo(accessToken);
	}
}
