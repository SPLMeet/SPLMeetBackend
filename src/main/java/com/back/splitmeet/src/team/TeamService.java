package com.back.splitmeet.src.team;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.RoleStatus;
import com.back.splitmeet.domain.UserInfo;
import com.back.splitmeet.domain.UserTeam;
import com.back.splitmeet.domain.repository.UserInfoRepository;
import com.back.splitmeet.domain.repository.UserTeamRepository;
import com.back.splitmeet.jwt.JwtTokenProvider;
import com.back.splitmeet.jwt.dto.TokenInfo;
import com.back.splitmeet.src.team.dto.PostCreateTeamRes;
import com.back.splitmeet.src.team.dto.TeamBanRes;
import com.back.splitmeet.util.BaseResponseStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {

	private final UserInfoRepository userInfoRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserTeamRepository userTeamRepository;

	public PostCreateTeamRes createTeam(String accessToken, String teamName) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo userinfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		if (userinfo == null) {
			return new PostCreateTeamRes(null);
		}
		if (userinfo.getTeamId() != 0) {
			return new PostCreateTeamRes(null);
		}
		UserTeam userTeam = new UserTeam(tokenInfo.getUserId(), teamName);
		userTeamRepository.save(userTeam);

		userinfo.setRole(RoleStatus.LEADER);
		userinfo.setTeamId(userTeam.getTeamId());
		userInfoRepository.save(userinfo);

		return new PostCreateTeamRes(userinfo.getUserId());
	}

	public TeamBanRes banUser(String accessToken, Long userId) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);

		UserInfo userInfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());
		UserInfo banUser = userInfoRepository.findOneByUserId(userId);

		if (userInfo.getRole() != RoleStatus.LEADER) {
			return new TeamBanRes(null);
		}

		if (!Objects.equals(userInfo.getTeamId(), banUser.getTeamId())) {
			return new TeamBanRes(null);
		} else {
			banUser.setRole(RoleStatus.NONE);
			banUser.setTeamId(0L);
			userInfoRepository.save(banUser);
			return new TeamBanRes(userId);
		}
	}

	public Boolean outTeam(String accessToken) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo userInfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());
		if (userInfo.getRole() != RoleStatus.NONE) {
			return false;
		}
		userInfo.setRole(RoleStatus.NONE);
		userInfo.setTeamId(0L);
		userInfoRepository.save(userInfo);
		return true;
	}

	// TODO: 팀 삭제와 동시에 유저 정보 수정 로직 추가
	// DONE
	public BaseResponseStatus deleteTeam(String accessToken) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo userInfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		if (userInfo.getRole() != RoleStatus.LEADER) {
			return BaseResponseStatus.TEAM_NOT_LEADER;
		}
		UserTeam userTeam = userTeamRepository.findOneByTeamId(userInfo.getTeamId());
		if (userTeam == null) {
			return BaseResponseStatus.TEAM_NOT_EXIST;
		}
		List<UserInfo> userInfos = userInfoRepository.findAllByTeamId(userInfo.getTeamId());
		for (UserInfo user : userInfos) {
			user.setTeamId(0L);
			user.setRole(RoleStatus.NONE);
			userInfoRepository.save(user);
		}
		userTeamRepository.delete(userTeam);
		return BaseResponseStatus.SUCCESS;
	}

	public BaseResponseStatus joinTeam(String accessToken, String userEmail) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo userInfo = userInfoRepository.findOneByUserEmail(userEmail);

		if (userInfo.getTeamId() != 0) {
			return BaseResponseStatus.ALREADY_IN_TEAM;
		}
		UserTeam userTeam = userTeamRepository.findOneByTeamId(userInfo.getTeamId());
		if (userTeam == null) {
			return BaseResponseStatus.TEAM_NOT_EXIST;
		}
		userInfo.setTeamId(userTeam.getTeamId());
		userInfo.setRole(RoleStatus.MEMBER);
		userInfoRepository.save(userInfo);
		return BaseResponseStatus.SUCCESS;
	}
}
