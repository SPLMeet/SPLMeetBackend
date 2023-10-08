package com.back.splitmeet.src.auth.dto;

import com.back.splitmeet.domain.RoleStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChkUserInfoRes {
	private Long userId;
	private Long teamId;
	private RoleStatus role;
	private String email;
	private String name;
	private String picture;
}
