package com.back.splitmeet.src.auth;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.UserInfo;
import com.back.splitmeet.domain.repository.UserInfoRepository;
import com.back.splitmeet.jwt.JwtTokenProvider;
import com.back.splitmeet.jwt.dto.TokenInfo;
import com.back.splitmeet.src.auth.dto.ChkUserInfoRes;

import io.jsonwebtoken.impl.Base64UrlCodec;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {
	private final UserInfoRepository userInfoRepository;
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	public AuthService(JwtTokenProvider jwtTokenProvider, UserInfoRepository userInfoRepository) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.userInfoRepository = userInfoRepository;
	}

	public String decryptBase64UrlToken(String idToken) {
		byte[] decode = new Base64UrlCodec().decode(idToken);
		return new String(decode, StandardCharsets.UTF_8);
	}

	public ChkUserInfoRes getUserInfo(String accessToken) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);

		UserInfo userInfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		Long teamId = 0L;
		if (userInfo != null && userInfo.getUserTeam() != null) {
			teamId = userInfo.getUserTeam().getTeamId();
		}

		ChkUserInfoRes chkUserInfoRes = ChkUserInfoRes.builder()
			.userId(tokenInfo.getUserId())
			.teamId(teamId)
			.role((userInfo != null) ? userInfo.getRole() : null)
			.email(tokenInfo.getEmail())
			.name(tokenInfo.getName())
			.picture(tokenInfo.getPicture()).build();
		
		return chkUserInfoRes;
	}
}
