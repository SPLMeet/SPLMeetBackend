package com.back.splitmeet.controller.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.repository.userInfoRepository;
import com.back.splitmeet.service.user.token.AuthTokensGenerator;
import com.back.splitmeet.user.entity.Member;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
	private final userInfoRepository memberRepository;
	private final AuthTokensGenerator authTokensGenerator;

	@GetMapping
	public ResponseEntity<List<Member>> findAll() {
		return ResponseEntity.ok(memberRepository.findAll());
	}

	// 토큰 검증 및 조회 과정 추가 필요
	@GetMapping("/{accessToken}")
	public ResponseEntity<Member> findByAccessToken(@PathVariable String accessToken) {
		Long memberId = authTokensGenerator.extractMemberId(accessToken);
		return ResponseEntity.ok(memberRepository.findById(memberId).get());
	}
}
