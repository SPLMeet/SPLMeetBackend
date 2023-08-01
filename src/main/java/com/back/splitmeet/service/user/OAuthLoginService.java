package com.back.splitmeet.service.user;

import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.user.userInfo;
import com.back.splitmeet.dto.user.KakaoLoginParams;
import com.back.splitmeet.dto.user.OAuthInfoResponse;
import com.back.splitmeet.dto.user.token.AuthTokens;
import com.back.splitmeet.repository.userInfoRepository;
import com.back.splitmeet.service.user.token.AuthTokensGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
	private final userInfoRepository userinfoRepository;
	private final AuthTokensGenerator authTokensGenerator;
	private final RequestOAuthInfoService requestOAuthInfoService;

	public AuthTokens login(KakaoLoginParams params) {
		OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
		Long useId = findOrCreateUserInfo(oAuthInfoResponse);
		return authTokensGenerator.generate(useId);
	}

	private Long findOrCreateUserInfo(OAuthInfoResponse oAuthInfoResponse) {
		return userinfoRepository.findByuserEmail(oAuthInfoResponse.getEmail())
			.map(userInfo::getUserId)
			.orElseGet(() -> newUserInfo(oAuthInfoResponse));
	}

	private Long newUserInfo(OAuthInfoResponse oAuthInfoResponse) {
		userInfo userinfo = userInfo.builder()
			.userEmail(oAuthInfoResponse.getEmail())
			.nickname(oAuthInfoResponse.getNickname())
			.oAuthProvider(oAuthInfoResponse.getOAuthProvider())
			.build();

		return userinfoRepository.save(userinfo).getUserId();
	}
}
