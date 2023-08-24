package com.back.splitmeet.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends Exception {
	private BaseResponseStatus status;

	public BaseException(BaseResponseStatus baseResponseStatus) {
	}
}
