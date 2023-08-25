package com.back.splitmeet.src.user;

import org.springframework.stereotype.Service;

import com.back.splitmeet.jwt.JwtTokenProvider;
import com.back.splitmeet.src.auth.AuthService;
import com.back.splitmeet.src.user.dto.GetMemberToIdtoken;
import com.back.splitmeet.src.user.dto.KakaoLoginRes;
import com.back.splitmeet.util.BaseException;
import com.back.splitmeet.util.BaseResponseStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	private final AuthService authService;
	private final JwtTokenProvider jwtTokenProvider;

	public UserService(AuthService authService, JwtTokenProvider jwtTokenProvider) {
		this.authService = authService;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public KakaoLoginRes KakaoService(GetMemberToIdtoken idToken, String action) {
		//
		//	DB 조회
		//
		// DB에 없으면 return false
		//
		//	DB 삽입(회원가입) 조건 action == create
		//
		KakaoLoginRes kakaoLoginRes = new KakaoLoginRes(
			jwtTokenProvider.createAccessToken(idToken.getEmail(),
				idToken.getNickname(), idToken.getPicture()),
			jwtTokenProvider.createRefreshToken(idToken.getEmail()));
		//
		//	DB 추가
		//
		return kakaoLoginRes;
	}

	public GetMemberToIdtoken tranJsonToGetMemberTo(String json) throws BaseException {
		try {
			ObjectMapper mapper = new
				ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			GetMemberToIdtoken dto = mapper.readValue(json, GetMemberToIdtoken.class);

			return dto;
		} catch (JsonProcessingException e) {
			throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
		}
	}
}
