package com.back.splitmeet.src.user;

import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.UserInfo;
import com.back.splitmeet.domain.repository.UserInfoRepository;
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
	private final UserInfoRepository userInfoRepository;

	public UserService(AuthService authService, JwtTokenProvider jwtTokenProvider,
		UserInfoRepository userInfoRepository) {
		this.authService = authService;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userInfoRepository = userInfoRepository;
	}

	public KakaoLoginRes KakaoService(GetMemberToIdtoken idToken, String action) {
		UserInfo userInfo = userInfoRepository.findByUserEmailAndUserName(idToken.getEmail(), idToken.getNickname());

		if ((userInfo.getUserEmail() == null || userInfo.getUserName() == null)) {
			return null;
		}
		if (action.equals("create")) {
			UserInfo userInfoCreate = UserInfo.createUser(idToken.getEmail(), idToken.getNickname(),
				idToken.getPicture());
			userInfoRepository.save(userInfoCreate);
		}
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
