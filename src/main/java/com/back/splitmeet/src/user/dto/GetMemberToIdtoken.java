package com.back.splitmeet.src.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GetMemberToIdtoken {
	private String nickname;
	private String email;
	private String picture;

	// 삭제 금지, 미사용으로 뜨지만 필요함 json 관리에 필요함
	public GetMemberToIdtoken() {
	}
}
