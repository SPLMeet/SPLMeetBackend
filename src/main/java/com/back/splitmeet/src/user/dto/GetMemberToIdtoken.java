package com.back.splitmeet.src.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetMemberToIdtoken {
	private String nickname;
	private String email;
	private String picture;
}
