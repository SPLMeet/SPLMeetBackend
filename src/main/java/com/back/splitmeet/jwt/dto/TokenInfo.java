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
	private String name;
	private String picture;

	public TokenInfo(long userId, String email, String name) {
		this.userId = userId;
		this.email = email;
		this.name = name;
	}
}
