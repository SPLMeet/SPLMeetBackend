package com.back.splitmeet.src.team;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.src.team.dto.PostCreateTeamRes;
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

	@PutMapping("/create")
	public BaseResponse<PostCreateTeamRes> createTeam(@RequestHeader("Authorization") String accessToken,
		@RequestParam String teamName) {
		PostCreateTeamRes postCreateTeamRes = teamService.createTeam(accessToken, teamName);

		return new BaseResponse<>(postCreateTeamRes);
	}

	@GetMapping("/ban")
	public BaseResponse<BaseResponseStatus> banUser(@RequestHeader("Authorization") String accessToken, @RequestParam
	Long userId) {
		return teamService.banUser(accessToken, userId).getUserId() == null ?
			new BaseResponse<>(BaseResponseStatus.INVALID_AUTH) :
			new BaseResponse<>(BaseResponseStatus.SUCCESS);
	}

	@GetMapping("/out")
	public BaseResponse<BaseResponseStatus> outTeam(@RequestHeader("Authorization") String accessToken) {
		Boolean teamOut = teamService.outTeam(accessToken);

		return teamOut ?
			new BaseResponse<>(BaseResponseStatus.SUCCESS) :
			new BaseResponse<>(BaseResponseStatus.LEADER_OR_NOT_MEMBER);
	}

	@GetMapping("/delete")
	public BaseResponse<BaseResponseStatus> deleteTeam(@RequestHeader("Authorization") String accessToken) {
		return new BaseResponse<>(teamService.deleteTeam(accessToken));
	}

	@PutMapping("/join")
	public BaseResponse<BaseResponseStatus> joinTeam(@RequestHeader("Authorization") String accessToken,
		@RequestParam String userEmail) {
		return new BaseResponse<>(teamService.joinTeam(accessToken, userEmail));
	}
}
