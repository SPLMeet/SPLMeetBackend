package com.back.splitmeet.src.split.dto;

import com.back.splitmeet.domain.RoleStatus;
import com.back.splitmeet.domain.SubmitMoneyStatus;
import com.back.splitmeet.domain.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class SplitCheckRes {
	private Long teamId;
	private RoleStatus role;
	private Long userId;
	private String userName;
	private String userProfile;
	private SubmitMoneyStatus submitMoney;

	@Builder
	public SplitCheckRes(UserInfo userInfo) {
		this.teamId = userInfo.getUserTeam().getTeamId();
		this.role = userInfo.getRole();
		this.userId = userInfo.getUserId();
		this.userName = userInfo.getUserName();
		this.userProfile = userInfo.getUserProfile();
		this.submitMoney = userInfo.getSubmitMoney();
	}
}
