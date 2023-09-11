package com.back.splitmeet.src.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChkUserInfoRes {
	private Long userId;
	private String email;
	private String name;
	private String picture;
}
