package com.back.splitmeet.src.team;

import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.UserInfo;
import com.back.splitmeet.domain.UserTeam;
import com.back.splitmeet.domain.repository.UserInfoRepository;
import com.back.splitmeet.domain.repository.UserTeamRepository;
import com.back.splitmeet.jwt.JwtTokenProvider;
import com.back.splitmeet.jwt.dto.TokenInfo;
import com.back.splitmeet.src.team.dto.PostCreateTeamRes;
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

	public PostCreateTeamRes createTeam(String accessToken, String teamName) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo userinfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		if (userinfo == null) {
			return null;
		}
		if (userinfo.getTeamId() != 0) {
			return new PostCreateTeamRes(0L);
		}
		// if (!(userinfo.getRole().equals(RoleStatus.LEADER))) {
		// 	return new PostCreateTeamRes(-1L);
		// }
		// UserTeam userTeam = userTeamRepository.save(UserTeam.builder()
		// 	.teamLeader(userinfo.getUserId())
		// 	.teamName(postCreateTeamReq.getTeam_name())
		// 	.leaderKakaoHash(postCreateTeamReq.getLeader_kakao_hash())
		// 	.build());
		//userinfo = UserInfo.createTeamLeader(userTeam.getTeamId());

		userInfoRepository.save(userinfo);
		return new PostCreateTeamRes(userinfo.getUserId());
	}

	public TeamBanRes banUser(String accessToken, Long userId) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);

		UserInfo userInfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());
		UserInfo banUser = userInfoRepository.findOneByUserId(userId);

		if (userInfo.getTeamId() != banUser.getTeamId()) {
			TeamBanRes teamBanRes = new TeamBanRes(null);
			return teamBanRes;
		} else {
			banUser.setRole(0);
			banUser.setTeamId(0L);
			userInfoRepository.save(banUser);
			TeamBanRes teamBanRes = new TeamBanRes(userId);
			return teamBanRes;
		}
	}

	public Boolean outTeam(String accessToken) {
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

	// TODO: 팀 삭제와 동시에 유저 정보 수정 로직 추가
	public BaseResponseStatus deleteTeam(String accessToken) {
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

	public BaseResponseStatus joinTeam(String accessToken, String userEmail) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo userInfo = userInfoRepository.findOneByUserEmail(userEmail);

		if (userInfo.getTeamId() != 0) {
			return BaseResponseStatus.ALREADY_IN_TEAM;
		}

		UserTeam userTeam = userTeamRepository.findOneByUserId(tokenInfo.getUserId());

		if (userTeam == null) {
			return BaseResponseStatus.TEAM_NOT_EXIST;
		}

		userInfo.setTeamId(userTeam.getTeamId());
		userInfo.setRole(1);
		userInfoRepository.save(userInfo);

		return BaseResponseStatus.SUCCESS;
	}
}
