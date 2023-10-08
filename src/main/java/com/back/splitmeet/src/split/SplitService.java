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
import com.back.splitmeet.src.split.dto.SplitEndRes;
import com.back.splitmeet.src.split.dto.SplitRegistReq;
import com.back.splitmeet.src.split.dto.SplitRegistRes;
import com.back.splitmeet.src.split.dto.SplitStartReq;
import com.back.splitmeet.src.split.dto.SplitStartRes;
import com.back.splitmeet.src.split.dto.SplitStatusRes;

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

	public SplitRegistRes regist(String accessToken, SplitRegistReq req) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo requester = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		if (requester.getUserTeam().getTeamId() == 0 || !requester.getRole().equals(RoleStatus.LEADER)) {
			return null;
		}
		requester.getUserTeam().setLeaderKakaoHash(req.getCode());
		userInfoRepository.save(requester);
		return SplitRegistRes.builder()
			.teamId(requester.getUserTeam().getTeamId())
			.build();
	}

	private String makeSplitUrl(String code, Long money) {
		return "https://qr.kakaopay.com/"
			+ code
			+ Long.toHexString(money.intValue() * 524288).toLowerCase();
	}

	public SplitStatusRes status(String accessToken) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo requester = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		Boolean isLeader = false;
		String url = null;
		Long money = 0L;
		if (requester.getUserTeam().getTeamId() == 0) {
			return null;
		}
		if (requester.getRole().equals(RoleStatus.LEADER)) {
			isLeader = true;
		}
		if (requester.getUserTeam().getTeamSettleStatus()) {
			money = requester.getUserTeam().getTeamTotalCost();
			url = makeSplitUrl(
				requester.getUserTeam().getLeaderKakaoHash(),
				money / requester.getUserTeam().getUserInfo().size()
			);
		}

		return SplitStatusRes.builder()
			.isLeader(isLeader)
			.url(url)
			.status(requester.getUserTeam().getTeamSettleStatus())
			.money(money)
			.build();
	}

	public SplitStartRes start(String accessToken, SplitStartReq req) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo requester = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		if (requester.getUserTeam().getTeamId() == 0 || !requester.getRole().equals(RoleStatus.LEADER)) {
			return null;
		}
		if (requester.getUserTeam().getTeamSettleStatus()) {
			return null;
		}
		requester.getUserTeam().setTeamSettleStatus(true);
		requester.getUserTeam().setTeamTotalCost(req.getMoney());
		userInfoRepository.save(requester);
		return SplitStartRes.builder()
			.teamSettleStatus(true)
			.build();
	}

	public SplitEndRes end(String accessToken) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo requester = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		if (requester.getUserTeam().getTeamId() == 0 || !requester.getRole().equals(RoleStatus.LEADER)) {
			return null;
		}
		if (!requester.getUserTeam().getTeamSettleStatus()) {
			return null;
		}
		requester.getUserTeam().setTeamSettleStatus(false);
		requester.getUserTeam().setTeamTotalCost(0L);
		userInfoRepository.save(requester);
		return SplitEndRes.builder()
			.teamSettleStatus(false)
			.build();
	}
}
