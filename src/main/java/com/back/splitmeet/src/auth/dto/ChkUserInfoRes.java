package com.back.splitmeet.src.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChkUserInfoRes {
	private String userName;
	private String userEmail;
	private Integer role;
	private boolean isAuth;
	private Integer teamId;
	private Integer userId;

}
