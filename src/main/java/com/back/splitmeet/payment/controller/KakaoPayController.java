package com.back.splitmeet.payment.controller;

import com.back.splitmeet.payment.dto.KakaoReadyResponse;
import com.back.splitmeet.payment.exception.BusinessLogicException;
import com.back.splitmeet.payment.service.KakaoPayService;
import com.back.splitmeet.payment.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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