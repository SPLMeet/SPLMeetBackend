package com.back.splitmeet.src.payment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.back.splitmeet.src.payment.dto.KakaoApproveResponse;
import com.back.splitmeet.src.payment.dto.KakaoReadyResponse;
import com.back.splitmeet.util.BaseResponse;
import com.back.splitmeet.util.BaseResponseStatus;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/pay")
@RequiredArgsConstructor
public class KakaoPayController {
	private final KakaoPayService kakaoPayService;

	// 결제 준비
	@GetMapping("/ready")
	public String readyToKakaoPay(Model model) {
		KakaoReadyResponse kakaoReadyResponse = kakaoPayService.kakaoPayReady();
		model.addAttribute("val", kakaoReadyResponse.getNext_redirect_pc_url());
		return "/api/pay/ready";
	}

	// 결제 성공
	@GetMapping("/success")
	@ResponseBody
	public BaseResponse<KakaoApproveResponse> paySuccess(@RequestParam("pg_token") String pgToken) {
		KakaoApproveResponse kakaoApproveResponse = kakaoPayService.ApproveResponse(pgToken);
		return new BaseResponse<>(kakaoApproveResponse);
	}

	// 결제 취소
	@GetMapping("/cancel")
	@ResponseBody
	public BaseResponse<String> payCancel() {
		return new BaseResponse<>(BaseResponseStatus.PAYMENT_CANCEL);
	}

	// 결제 실패
	@GetMapping("/fail")
	@ResponseBody
	public BaseResponse<String> payFail() {
		return new BaseResponse(BaseResponseStatus.PAYMENT_FAIL);
	}
}
