package com.back.splitmeet.util;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
	SUCCESS(true, 1000, "요청에 성공하였습니다."),
	EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
	INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
	INVALID_USER_JWT(false, 2004, "권한이 없는 유저의 접근입니다."),
	INVALID_AUTH(false, 2005, "유효하지 않은 회원 정보입니다."),
	USERS_EMPTY_USER_EMAIL(false, 2011, "유저 이메일 값을 확인해주세요."),
	POST_USERS_EXISTS_EMAIL(false, 2017, "중복된 이메일입니다."),
	DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
	DELETE_USER_FAIL(false, 4008, "유저 정보 삭제에 실패하였습니다."),
	SURVEY_DAY(false, 5031, "설문은 하루에 한 번만 가능 합니다.");;

	private final boolean isSuccess;
	private final int code;
	private final String message;

	private BaseResponseStatus(boolean isSuccess, int code, String message) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.message = message;
	}
}
