package com.back.splitmeet.src.payment;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.back.splitmeet.src.payment.dto.KakaoApproveResponse;
import com.back.splitmeet.src.payment.dto.KakaoReadyResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayService {
	private KakaoReadyResponse kakaoReady;
	static final String cid = "TC0ONETIME";
	static final String adminKey = "51769a16c33c221845d490c98408757a";

	public KakaoReadyResponse kakaoPayReady() {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("cid", cid);
		parameters.add("partner_order_id", "test_num");
		parameters.add("partner_user_id", "test_id");
		parameters.add("item_name", "test_name");
		parameters.add("quantity", "1");
		parameters.add("total_amount", "10000");
		parameters.add("tax_free_amount", "0");
		parameters.add("approval_url", "http://localhost:8080/api/pay/success");
		parameters.add("cancel_url", "http://localhost:8080/api/pay/cancel");
		parameters.add("fail_url", "http://localhost:8080/api/pay/fail");

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

		RestTemplate restTemplate = new RestTemplate();

		kakaoReady = restTemplate.postForObject("https://kapi.kakao.com/v1/payment/ready",
			requestEntity,
			KakaoReadyResponse.class);

		return kakaoReady;
	}

	public KakaoApproveResponse ApproveResponse(String pgToken) {

		// 카카오 요청
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("cid", cid);
		parameters.add("tid", kakaoReady.getTid());
		parameters.add("partner_order_id", "test_num");
		parameters.add("partner_user_id", "test_id");
		parameters.add("pg_token", pgToken);

		// 파라미터, 헤더
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

		// 외부에 보낼 url
		RestTemplate restTemplate = new RestTemplate();

		KakaoApproveResponse approveResponse = restTemplate.postForObject(
			"https://kapi.kakao.com/v1/payment/approve",
			requestEntity,
			KakaoApproveResponse.class);

		return approveResponse;
	}

	private HttpHeaders getHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();

		String auth = "KakaoAK " + adminKey;

		httpHeaders.set("Authorization", auth);
		httpHeaders.set("Content-type", "application/x-www-form-urlencoded");

		return httpHeaders;
	}
}
