package com.back.splitmeet.src.team.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamBanRes {
	private String userName;
	private String userEmail;
	private Integer role;
	private Long teamId;
	private Long userId;

	public TeamBanRes(Long userId) {
		this.userId = userId;
	}
}
