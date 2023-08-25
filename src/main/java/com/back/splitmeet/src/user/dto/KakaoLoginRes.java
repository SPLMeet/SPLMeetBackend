package com.back.splitmeet.src.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class KakaoLoginRes {
	private String accessToken;
	private String refreshToken;
}
