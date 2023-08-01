package com.back.splitmeet.controller.payment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.dto.payment.KakaoReadyResponse;
import com.back.splitmeet.exception.payment.BusinessLogicException;
import com.back.splitmeet.exception.payment.ExceptionCode;
import com.back.splitmeet.service.payment.KakaoPayService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/groupbuy")
@RequiredArgsConstructor
public class KakaoPayController {

	private final KakaoPayService kakaoPayService;

	//  결제요청
	@GetMapping("/")
	public KakaoReadyResponse readyToKakaoPay() {
		return kakaoPayService.kakaoPayReady();
	}

	//  결제 진행 중 취소
	@GetMapping("/cancel")
	public void cancel() {
		throw new BusinessLogicException(ExceptionCode.PAY_CANCEL);
	}

	//  결제 실패
	@GetMapping("/fail")
	public void fail() {
		throw new BusinessLogicException(ExceptionCode.PAY_FAILED);
	}
}