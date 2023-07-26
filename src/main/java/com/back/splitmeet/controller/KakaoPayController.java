package com.back.splitmeet.controller;

import com.back.splitmeet.dto.KakaoReadyResponse;
import com.back.splitmeet.exception.BusinessLogicException;
import com.back.splitmeet.service.KakaoPayService;
import com.back.splitmeet.exception.ExceptionCode;

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