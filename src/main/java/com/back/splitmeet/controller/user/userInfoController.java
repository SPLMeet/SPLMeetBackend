package com.back.splitmeet.controller.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.domain.user.userInfo;
import com.back.splitmeet.repository.userInfoRepository;
import com.back.splitmeet.service.user.token.AuthTokensGenerator;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userInfos")
public class userInfoController {
	private final userInfoRepository userinfoRepository;
	private final AuthTokensGenerator authTokensGenerator;

	@GetMapping
	public ResponseEntity<List<userInfo>> findAll() {
		return ResponseEntity.ok(userinfoRepository.findAll());
	}

	// 토큰 검증 및 조회 과정 추가 필요
	@GetMapping("/{accessToken}")
	public ResponseEntity<userInfo> findByAccessToken(@PathVariable String accessToken) {
		Long useId = authTokensGenerator.extractUserId(accessToken);
		return ResponseEntity.ok(userinfoRepository.findById(useId).get());
	}
}
