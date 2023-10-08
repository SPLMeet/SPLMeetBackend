package com.back.splitmeet.src.team;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.splitmeet.domain.RoleStatus;
import com.back.splitmeet.domain.UserInfo;
import com.back.splitmeet.domain.UserTeam;
import com.back.splitmeet.domain.repository.UserInfoRepository;
import com.back.splitmeet.domain.repository.UserTeamRepository;
import com.back.splitmeet.jwt.JwtTokenProvider;
import com.back.splitmeet.jwt.dto.TokenInfo;
import com.back.splitmeet.src.team.dto.GetTeamMemberRes;
import com.back.splitmeet.src.team.dto.GetTeamTotalRes;
import com.back.splitmeet.src.team.dto.TeamBanRes;
import com.back.splitmeet.util.BaseResponseStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {

	private final UserInfoRepository userInfoRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserTeamRepository userTeamRepository;

	/**
	 * 팀 생성
	 *
	 * @param accessToken
	 * @param teamName
	 * @return
	 */
	@Transactional
	public Long createTeam(String accessToken, String teamName) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo userinfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		if (userinfo == null || userinfo.getUserTeam() != null) {
			return null;
		}

		if (userinfo.getRole() != RoleStatus.NONE) {
			return null;
		}

		UserTeam userTeam = new UserTeam();
		userTeam.setTeamLeader(userinfo.getUserId());
		userTeam.setTeamName(teamName);

		userTeamRepository.save(userTeam);

		userinfo.setUserTeam(userTeam);
		userinfo.setRole(RoleStatus.LEADER);
		userInfoRepository.save(userinfo);

		return userTeam.getTeamId();
	}

	/**
	 * 팀 가입
	 *
	 * @param accessToken
	 * @param userId
	 * @return
	 */
	@Transactional
	public TeamBanRes banUser(String accessToken, Long userId) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);

		UserInfo userInfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());
		UserInfo banUser = userInfoRepository.findOneByUserId(userId);

		if (userInfo.getRole() != RoleStatus.LEADER) {
			return new TeamBanRes(null);
		}

		UserTeam banUserTeam = banUser.getUserTeam();

		if (banUserTeam == null || !banUser.getUserTeam().getTeamId().equals(userInfo.getUserTeam().getTeamId())) {
			return new TeamBanRes(null);
		} else {
			banUser.setRole(RoleStatus.NONE);
			// 유저의 팀 연결을 해제 (팀에서 벤)
			banUser.setUserTeam(null);
			// UserTeam 엔터티에서도 유저를 제거
			banUserTeam.getUserInfo().remove(banUser);

			// 변경사항을 데이터베이스에 저장
			userInfoRepository.save(banUser);
			userTeamRepository.save(banUserTeam);
			return new TeamBanRes(userId);
		}
	}

	/**
	 * 팀 탈퇴
	 *
	 * @param accessToken
	 * @return
	 */
	@Transactional
	public BaseResponseStatus leaveTeam(String accessToken) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo userInfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());
		UserTeam userTeam = userInfo.getUserTeam();
		if (userInfo.getRole() != RoleStatus.MEMBER) {
			return BaseResponseStatus.TEAM_NOT_MEMBER;
		}
		userInfo.setUserTeam(null);
		userInfo.setRole(RoleStatus.NONE);
		userTeam.getUserInfo().remove(userInfo);
		userInfoRepository.save(userInfo);
		userTeamRepository.save(userTeam);
		return BaseResponseStatus.SUCCESS;
	}

	// TODO: 팀 삭제와 동시에 유저 정보 수정 로직 추가

	/**
	 * 팀 삭제
	 *
	 * @param accessToken
	 * @return
	 */
	@Transactional
	public BaseResponseStatus deleteTeam(String accessToken) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo userInfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		if (userInfo.getRole() != RoleStatus.LEADER) {
			return BaseResponseStatus.TEAM_NOT_LEADER;
		}
		UserTeam userTeam = userInfo.getUserTeam();
		if (userTeam == null) {
			return BaseResponseStatus.TEAM_NOT_EXIST;
		}

		// 팀에 속한 모든 사용자의 팀 정보를 null로 설정
		List<UserInfo> teamMembers = userInfoRepository.findAllByUserTeam(userTeam);
		for (UserInfo member : teamMembers) {
			member.setUserTeam(null);
			member.setRole(RoleStatus.NONE);
		}
		userInfoRepository.saveAll(teamMembers);
		userTeamRepository.delete(userTeam);
		return BaseResponseStatus.SUCCESS;
	}

	/**
	 * 팀 가입
	 *
	 * @param accessToken
	 * @param userEmail
	 * @return
	 */
	@Transactional
	public BaseResponseStatus joinTeam(String accessToken, String userEmail) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo inviteUserInfo = userInfoRepository.findOneByUserEmail(userEmail);
		UserInfo ownerUserInfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		if (inviteUserInfo.getUserTeam() != null) {
			return BaseResponseStatus.ALREADY_IN_TEAM;
		}

		if (ownerUserInfo.getRole() != RoleStatus.LEADER) {
			return BaseResponseStatus.TEAM_NOT_LEADER;
		}

		UserTeam ownerUserTeam = ownerUserInfo.getUserTeam();
		inviteUserInfo.setUserTeam(ownerUserTeam);
		inviteUserInfo.setRole(RoleStatus.MEMBER);
		userInfoRepository.save(inviteUserInfo);
		return BaseResponseStatus.SUCCESS;
	}

	/**
	 * 팀원 목록 조회
	 *
	 * @param accessToken
	 * @return
	 */
	@Transactional(readOnly = true)
	public GetTeamTotalRes getTeamMembers(String accessToken) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo userInfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		// 유저가 속한 팀을 찾음
		UserTeam userTeam = userInfo.getUserTeam();
		if (userTeam == null) {
			throw new IllegalArgumentException("The user is not a member of any team");
		}

		// 팀에 속한 모든 유저들을 불러옴
		List<UserInfo> teamMembers = new ArrayList<>(userTeam.getUserInfo());

		// 유저들의 정보를 UserDTO 리스트로 변환
		List<GetTeamMemberRes> teamMemberDTOs = teamMembers.stream()
			.map(member -> GetTeamMemberRes.builder()
				.userName(member.getUserName())
				.userProfile(member.getUserProfile())
				.role(member.getRole())
				.build())
			.toList();

		GetTeamTotalRes getTeamTotalRes = new GetTeamTotalRes(userTeam.getTeamName(), userTeam.getStartDate(),
			userTeam.getEndDate(), userTeam.getTeamTotalCost(), teamMemberDTOs);
		return getTeamTotalRes;
	}

}
