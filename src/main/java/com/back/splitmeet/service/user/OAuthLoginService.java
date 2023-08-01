package com.back.splitmeet.service.user;

import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.user.userInfo;
import com.back.splitmeet.dto.OAuthInfoResponse;
import com.back.splitmeet.user.KakaoLoginParams;
import com.back.splitmeet.user.repository.userInfoRepository;
import com.back.splitmeet.user.token.AuthTokens;
import com.back.splitmeet.user.token.AuthTokensGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
	private final userInfoRepository userinfoRepository;
	private final AuthTokensGenerator authTokensGenerator;
	private final RequestOAuthInfoService requestOAuthInfoService;

	public AuthTokens login(KakaoLoginParams params) {
		OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
		Long memberId = findOrCreateMember(oAuthInfoResponse);
		return authTokensGenerator.generate(memberId);
	}

	private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
		return userinfoRepository.findByEmail(oAuthInfoResponse.getEmail())
			.map(userInfo::getUserId)
			.orElseGet(() -> newMember(oAuthInfoResponse));
	}

	private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
		userInfo userinfo = userInfo.builder()
			.email(oAuthInfoResponse.getEmail())
			.nickname(oAuthInfoResponse.getNickname())
			.oAuthProvider(oAuthInfoResponse.getOAuthProvider())
			.build();

		return userinfoRepository.save(userinfo).getUserId();
	}
}
