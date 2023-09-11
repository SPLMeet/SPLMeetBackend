package com.back.splitmeet.src.auth;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.splitmeet.jwt.JwtTokenProvider;
import com.back.splitmeet.jwt.dto.TokenInfo;
import com.back.splitmeet.src.auth.dto.ChkUserInfoRes;

import io.jsonwebtoken.impl.Base64UrlCodec;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	public AuthService(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public String decryptBase64UrlToken(String idToken) {
		byte[] decode = new Base64UrlCodec().decode(idToken);
		return new String(decode, StandardCharsets.UTF_8);
	}

	public ChkUserInfoRes getUserInfo(String accessToken) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		ChkUserInfoRes chkUserInfoRes = new ChkUserInfoRes(tokenInfo.getUserId(), tokenInfo.getEmail(),
			tokenInfo.getName(), tokenInfo.getPicture());
		return chkUserInfoRes;
	}
}
