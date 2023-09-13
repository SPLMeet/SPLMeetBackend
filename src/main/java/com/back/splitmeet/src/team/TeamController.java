package com.back.splitmeet.src.team;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.src.team.dto.TeamBanRes;
import com.back.splitmeet.util.BaseResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamController {
	private final TeamService teamService;

	@GetMapping("/ban")
	public BaseResponse<TeamBanRes> ban(@RequestHeader("Authorization") String accessToken, @RequestParam
	Long userId) {
		return new BaseResponse<>(teamService.TeamBan(accessToken, userId));
	}
}
