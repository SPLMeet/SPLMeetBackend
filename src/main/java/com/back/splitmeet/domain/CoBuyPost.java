package com.back.splitmeet.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "coBuyPost")
@Getter
@Setter
public class CoBuyPost {
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long localId; // 게시물 Id

	private Integer localMoney; // 게시물 금액

	@Column(nullable = false, length = 50)
	private String localName; // 게시물 이름

	@Column(nullable = false, columnDefinition = "TEXT")
	private String localDesription; // 게시물 설명

	@Column(nullable = false, length = 100)
	private String localAddress; // 게시물 주소

	@Column(nullable = false)
	private Long targetNumber; // 목표 인원

	@Column(nullable = false)
	private Long status; // 0 : 공구 중, 1 : 공구 완료, 2 : 공구 실패

	@Column(nullable = false)
	private LocalDateTime timeLimit; // 게시물 시간 제한

	@OneToMany
	@JoinColumn(name = "PostImg")
	private List<CoBuyPostImg> cobuypostImgs = new ArrayList<>(); // 게시물 이미지
}
