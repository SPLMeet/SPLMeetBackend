package com.back.splitmeet.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TokenInfo {
	private Long userId;
	private String email;
	private Integer role;
	private String name;
	private String picture;
	private Long teamId;

	public TokenInfo(long userId, String email, String name) {
		this.userId = userId;
		this.email = email;
		this.name = name;
	}
}
