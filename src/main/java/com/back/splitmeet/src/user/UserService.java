package com.back.splitmeet.src.user;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.RoleStatus;
import com.back.splitmeet.domain.UserInfo;
import com.back.splitmeet.domain.repository.PayListRepository;
import com.back.splitmeet.domain.repository.UserInfoRepository;
import com.back.splitmeet.jwt.JwtTokenProvider;
import com.back.splitmeet.jwt.dto.TokenInfo;
import com.back.splitmeet.src.user.dto.GetMemberToIdtoken;
import com.back.splitmeet.src.user.dto.GetReceiptRes;
import com.back.splitmeet.src.user.dto.KakaoLoginRes;
import com.back.splitmeet.util.BaseResponseStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	private final JwtTokenProvider jwtTokenProvider;
	private final UserInfoRepository userInfoRepository;
	private final PayListRepository payListRepository;

	@Autowired
	public UserService(JwtTokenProvider jwtTokenProvider,
		UserInfoRepository userInfoRepository, PayListRepository payListRepository) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.userInfoRepository = userInfoRepository;
		this.payListRepository = payListRepository;
	}

	public KakaoLoginRes kakaoLogin(GetMemberToIdtoken idToken, String action) {
		KakaoLoginRes kakaoLoginRes = new KakaoLoginRes(null, null);

		UserInfo userInfo = userInfoRepository.findOneByUserEmailAndUserName(idToken.getEmail(), idToken.getNickname());

		if (action.equals("signup") && userInfo == null) {
			log.info("1");
			UserInfo userInfoCreate = UserInfo.createUser(idToken.getEmail(), idToken.getNickname(),
				idToken.getPicture());

			userInfoRepository.save(userInfoCreate);

			userInfo = userInfoRepository.findOneByUserEmailAndUserName(idToken.getEmail(),
				idToken.getNickname());
			userInfo.setRole(RoleStatus.NONE);

			setUserToken(idToken, kakaoLoginRes, userInfo);
		} else if (action.equals("signin") && userInfo != null) {
			log.info("3");
			setUserToken(idToken, kakaoLoginRes, userInfo);
		} else if (action.equals("signup")) {
			log.info("2");
			kakaoLoginRes.setAccessToken(null);
			kakaoLoginRes.setRefreshToken(null);
		} else if (action.equals("signin")) {
			log.info("4");
			kakaoLoginRes.setAccessToken(null);
			kakaoLoginRes.setRefreshToken(null);
		}
		return kakaoLoginRes;
	}

	public BaseResponseStatus userLogout(String accessToken) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo userinfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		if (userinfo == null) {
			return BaseResponseStatus.INVALID_JWT;
		}

		userinfo.setAccessToken(null);
		userinfo.setRefreshToken(null);

		userInfoRepository.save(userinfo);

		return BaseResponseStatus.SUCCESS;
	}

	public BaseResponseStatus userDelete(Long userId, String accessToken) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo userinfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		if (tokenInfo.getUserId() != userId) {
			return BaseResponseStatus.INVALID_AUTH;
		} else if (userinfo == null) {
			return BaseResponseStatus.INVALID_AUTH;
		}

		userInfoRepository.delete(userinfo);

		return BaseResponseStatus.SUCCESS;
	}

	private void setUserToken(GetMemberToIdtoken idToken, KakaoLoginRes kakaoLoginRes, UserInfo userInfo) {
		String accessToken = jwtTokenProvider.createAccessToken(userInfo.getUserId(),
			userInfo.getRole(),
			idToken.getEmail(),
			idToken.getNickname(), idToken.getPicture());

		String refreshToken = jwtTokenProvider.createRefreshToken(userInfo.getUserId(), idToken.getEmail(),
			idToken.getNickname());

		userInfo.setAccessToken(accessToken);
		userInfo.setRefreshToken(refreshToken);

		userInfoRepository.save(userInfo);

		kakaoLoginRes.setAccessToken(accessToken);
		kakaoLoginRes.setRefreshToken(refreshToken);
	}

	public List<GetReceiptRes> getReceipt(String accessToken, Long userId) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo userinfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		if (userinfo == null) {
			return null;
		}

		if (!Objects.equals(userinfo.getUserId(), userId)) {
			return null;
		}

		return payListRepository.findALLByUserinfo(userinfo);
	}

	public GetMemberToIdtoken tranJsonToGetMemberTo(String json) {
		try {
			ObjectMapper mapper = new
				ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			GetMemberToIdtoken dto = mapper.readValue(json, GetMemberToIdtoken.class);

			return dto;
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
