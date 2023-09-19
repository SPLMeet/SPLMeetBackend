package com.back.splitmeet.src.split.dto;

import com.back.splitmeet.domain.RoleStatus;
import com.back.splitmeet.domain.SubmitMoneyStatus;

import lombok.AllArgsConstructor;
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
}
