package com.back.splitmeet.src.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchUserInfoRes {
	private Long userId;
	private String email;
	private String name;
	private String picture;
}
