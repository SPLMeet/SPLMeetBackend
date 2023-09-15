package com.back.splitmeet.src.team;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.jwt.JwtTokenProvider;
import com.back.splitmeet.src.team.dto.TeamBanRes;
import com.back.splitmeet.util.BaseResponse;
import com.back.splitmeet.util.BaseResponseStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamController {
	private final TeamService teamService;
	private final JwtTokenProvider jwtTokenProvider;

	@GetMapping("/ban")
	public BaseResponse<BaseResponseStatus> teamBan(@RequestHeader("Authorization") String accessToken, @RequestParam
	Long userId) {
		Integer Role = jwtTokenProvider.getRoles(jwtTokenProvider.getUserInfoFromAcs(accessToken).getEmail()).getRole();
		if (Role != 2) {
			return new BaseResponse<>(BaseResponseStatus.INVALID_AUTH);
		}

		TeamBanRes teamBanRes = teamService.TeamBan(accessToken, userId);

		if (teamBanRes.getUserId() == null) {
			return new BaseResponse<>(BaseResponseStatus.INVALID_AUTH);
		} else {
			return new BaseResponse<>(BaseResponseStatus.SUCCESS);
		}
	}

	@GetMapping("/out")
	public BaseResponse<BaseResponseStatus> teamOut(@RequestHeader("Authorization") String accessToken) {
		Boolean teamOut = teamService.TeamOut(accessToken);
		if (teamOut) {
			return new BaseResponse<>(BaseResponseStatus.LEADER_OR_NOT_MEMBER);
		}
		return new BaseResponse<>(BaseResponseStatus.SUCCESS);
	}

	@GetMapping("/delete")
	public BaseResponse<BaseResponseStatus> teamDelete(@RequestHeader("Authorization") String accessToken) {
		return new BaseResponse<>(teamService.TeamDelete(accessToken));
	}

	@PutMapping("/join")
	public BaseResponse<BaseResponseStatus> teamJoin(@RequestHeader("Authorization") String accessToken,
		@RequestParam String userEmail) {
		return new BaseResponse<>(teamService.TeamJoin(accessToken, userEmail));
	}
}
