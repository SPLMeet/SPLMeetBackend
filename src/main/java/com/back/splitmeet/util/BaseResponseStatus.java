package com.back.splitmeet.util;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
	SUCCESS(true, 1000, "요청에 성공하였습니다."),
	EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
	INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
	INVALID_TOKEN(false, 2003, "유효하지 않은 토큰 입니다."),
	INVALID_USER_JWT(false, 2004, "권한이 없는 유저의 접근입니다."),
	EMPTY_TOKEN(false, 2006, "토큰이 비어있습니다"),
	// 2050 번대 -> payment status
	PAYMENT_FAIL(false, 2050, "결제에 실패하였습니다."),
	PAYMENT_CANCEL(false, 2051, "결제가 취소되었습니다."),
	// 2060 번대 -> user status
	USERS_EMPTY_USER_EMAIL(false, 2061, "유저 이메일 값을 확인해주세요."),
	NOT_SIGNED(false, 2062, "미가입된 이메일 입니다"),
	POST_USERS_EXISTS_EMAIL(false, 2063, "중복된 이메일입니다."),
	INVALID_AUTH(false, 2064, "유효하지 않은 회원 정보입니다."),
	// 2070 번대 -> team status
	INVALID_TEAM(false, 2071, "유효하지 않은 팀 정보입니다."),
	LEADER_CANNOT_LEAVE(false, 2072, "팀장은 팀을 나갈 수 없습니다."),
	TEAM_NOT_FOUND(false, 2073, "팀을 찾을 수 없습니다."),
	TEAM_ALREADY_EXIST(false, 2074, "이미 존재하는 팀입니다."),
	TEAM_NOT_EXIST(false, 2075, "존재하지 않는 팀입니다."),
	TEAM_NOT_LEADER(false, 2076, "팀장이 아닙니다."),
	TEAM_NOT_MEMBER(false, 2077, "팀원이 아닙니다."),
	NOT_IN_TEAM(false, 2078, "팀에 속해있지 않습니다."),
	LEADER_OR_NOT_MEMBER(false, 2079, "팀장이거나 팀원이 아닙니다."),
	ALREADY_IN_TEAM(false, 2080, "이미 팀에 속해있습니다."),

	//3000번대 -> schedule status
	ALREADY_IN_SCHEDULE(false, 3000, "이미 등록된 스케줄 입니다"),
	SCHEDULE_NOT_CHANGED(false, 3001, "기존 스케줄과 바뀌지 않았습니다"),
	SCHEDULE_ADD_FAIL(false, 3002, "스케줄 추가에 실패했습니다"),

	DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
	DELETE_USER_FAIL(false, 4008, "유저 정보 삭제에 실패하였습니다."),

	SURVEY_DAY(false, 5031, "설문은 하루에 한 번만 가능 합니다."),

	NOT_CONFIGURED_ERROR(false, 6001, "미지정 에러");

	private final boolean isSuccess;
	private final int code;
	private final String message;

	private BaseResponseStatus(boolean isSuccess, int code, String message) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.message = message;
	}
}
