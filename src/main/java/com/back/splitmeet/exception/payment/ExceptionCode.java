package com.back.splitmeet.exception.payment;

import lombok.Getter;

public enum ExceptionCode {
	PAY_CANCEL(404, "Payments get cancel"),
	PAY_FAILED(404, "Payment get failed");

	@Getter
	private int status;

	@Getter
	private String message;

	ExceptionCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
