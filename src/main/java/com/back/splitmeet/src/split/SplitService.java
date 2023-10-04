package com.back.splitmeet.src.split;

import java.util.List;

import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.RoleStatus;
import com.back.splitmeet.domain.UserInfo;
import com.back.splitmeet.domain.repository.UserInfoRepository;
import com.back.splitmeet.domain.repository.UserTeamRepository;
import com.back.splitmeet.jwt.JwtTokenProvider;
import com.back.splitmeet.jwt.dto.TokenInfo;
import com.back.splitmeet.src.split.dto.SplitCheckRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SplitService {
	private final JwtTokenProvider jwtTokenProvider;
	private final UserInfoRepository userInfoRepository;
	private final UserTeamRepository userTeamRepository;

	public List<SplitCheckRes> checkSplit(String accessToken) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo requester = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		if (requester.getUserTeam().getTeamId() == 0 || !requester.getRole().equals(RoleStatus.LEADER)) {
			return null;
		}
		return requester.getUserTeam().getUserInfo().stream()
			.map(SplitCheckRes::new).toList();
	}
}
