package com.back.splitmeet.src.team;

import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.UserInfo;
import com.back.splitmeet.domain.repository.UserInfoRepository;
import com.back.splitmeet.jwt.JwtTokenProvider;
import com.back.splitmeet.jwt.dto.TokenInfo;
import com.back.splitmeet.src.team.dto.TeamBanRes;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeamService {
	private final JwtTokenProvider jwtTokenProvider;
	private final UserInfoRepository userInfoRepository;

	public TeamService(JwtTokenProvider jwtTokenProvider, UserInfoRepository userInfoRepository) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.userInfoRepository = userInfoRepository;
	}

	public TeamBanRes TeamBan(String accessToken, Long userId) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);

		UserInfo userInfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());
		UserInfo banUser = userInfoRepository.findOneByUserId(userId);

		if (userInfo.getTeamId() != banUser.getTeamId()) {
			TeamBanRes teamBanRes = new TeamBanRes(null);
			return teamBanRes;
		} else {
			banUser.setRole(0);
			userInfoRepository.save(banUser);
			TeamBanRes teamBanRes = new TeamBanRes(userId);
			return teamBanRes;
		}
	}
}
