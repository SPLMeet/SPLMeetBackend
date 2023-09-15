package com.back.splitmeet.src.team;

import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.UserInfo;
import com.back.splitmeet.domain.UserTeam;
import com.back.splitmeet.domain.repository.UserInfoRepository;
import com.back.splitmeet.domain.repository.UserTeamRepository;
import com.back.splitmeet.jwt.JwtTokenProvider;
import com.back.splitmeet.jwt.dto.TokenInfo;
import com.back.splitmeet.src.team.dto.TeamBanRes;
import com.back.splitmeet.util.BaseResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeamService {
	private final JwtTokenProvider jwtTokenProvider;
	private final UserInfoRepository userInfoRepository;
	private final UserTeamRepository userTeamRepository;

	public TeamService(JwtTokenProvider jwtTokenProvider, UserInfoRepository userInfoRepository,
		UserTeamRepository userTeamRepository) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.userInfoRepository = userInfoRepository;
		this.userTeamRepository = userTeamRepository;
	}

	public TeamBanRes TeamBan(String accessToken, Long userId) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);

		UserInfo userInfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());
		UserInfo banUser = userInfoRepository.findOneByUserId(userId);

		if (userInfo.getTeamId() != banUser.getTeamId()) {
			TeamBanRes teamBanRes = new TeamBanRes(null);
			return teamBanRes;
		} else {
			//	private String userName;
			//	private String userEmail;
			//	private Integer role;
			//	private Long teamId;
			//	private Long userId;
			banUser.setRole(0);
			banUser.setTeamId(0L);
			userInfoRepository.save(banUser);
			TeamBanRes teamBanRes = new TeamBanRes(userId);
			return teamBanRes;
		}
	}

	public Boolean TeamOut(String accessToken) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo userInfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());
		if (userInfo.getRole() != 1) {
			return false;
		}
		userInfo.setRole(0);
		userInfo.setTeamId(0L);
		userInfoRepository.save(userInfo);
		return true;
	}

	public BaseResponseStatus TeamDelete(String accessToken) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo userInfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		if (userInfo.getRole() != 2) {
			return BaseResponseStatus.TEAM_NOT_LEADER;
		}

		UserTeam userTeam = userTeamRepository.findOneByTeamId(userInfo.getTeamId());

		if (userTeam == null) {
			return BaseResponseStatus.TEAM_NOT_EXIST;
		}

		userTeamRepository.delete(userTeam);

		return BaseResponseStatus.SUCCESS;
	}

	public BaseResponseStatus TeamJoin(String accessToken, String userEmail) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo userInfo = userInfoRepository.findOneByUserEmail(userEmail);

		if (userInfo.getTeamId() != 0) {
			return BaseResponseStatus.ALREADY_IN_TEAM;
		}

		UserTeam userTeam = userTeamRepository.findOneByTeamId(tokenInfo.getTeamId());

		if (userTeam == null) {
			return BaseResponseStatus.TEAM_NOT_EXIST;
		}

		userInfo.setTeamId(userTeam.getTeamId());
		userInfo.setRole(1);
		userInfoRepository.save(userInfo);

		return BaseResponseStatus.SUCCESS;
	}
}
