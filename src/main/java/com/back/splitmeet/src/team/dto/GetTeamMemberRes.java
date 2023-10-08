package com.back.splitmeet.src.team.dto;

import com.back.splitmeet.domain.RoleStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 유저 이름
 * 유저 프로필 사진
 * 유저 역할
 */

@Builder
@AllArgsConstructor
@Data
public class GetTeamMemberRes {

	private String userName;
	private String userProfile;
	private RoleStatus role;
}
