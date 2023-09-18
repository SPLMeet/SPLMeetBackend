package com.back.splitmeet.src.team;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.splitmeet.domain.UserInfo;
import com.back.splitmeet.domain.UserTeam;
import com.back.splitmeet.domain.repository.UserInfoRepository;
import com.back.splitmeet.domain.repository.UserTeamRepository;
import com.back.splitmeet.jwt.JwtTokenProvider;

import com.back.splitmeet.src.team.dto.PostCreateTeamRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {

	private final UserInfoRepository userInfoRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserTeamRepository userTeamRepository;

	@Transactional
	public PostCreateTeamRes createRoom(String token) {

		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(token);
		UserInfo userinfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());
		if (userinfo == null) {
			return null;
		}
		if (userinfo.getTeamId() != 0) {
			return new PostCreateTeamRes(0L);
		}

		if (userinfo.getRole() != 0) {
			return new PostCreateTeamRes(-1L);
		}
		UserTeam userTeam = userTeamRepository.save(UserTeam.builder().idx(userinfo.getUserId()).build());
		userinfo.setTeamId(userTeam.getTeamId());
		userinfo.setRole(2);
		userInfoRepository.save(userinfo);
		//parkingLotRepository.saveAll(ParkingLotCreator.getParkingLotCreator(userTeam.getIdx()));
		return new PostCreateTeamRes(userinfo.getUserId());
	}
}
