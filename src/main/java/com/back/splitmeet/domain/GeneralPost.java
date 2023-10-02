package com.back.splitmeet.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "generalPost")
@Getter
@Setter
@NoArgsConstructor
public class GeneralPost {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long localId; // 게시물 Id

	@Column(nullable = false, length = 50)
	private String localName; // 게시물 이름

	@Column(nullable = false, length = 100)
	private String localMoneyDescription; // 금액 설명

	@Column(nullable = false, columnDefinition = "TEXT")
	private String localWeb; // 게시물 웹사이트

	@Column(nullable = false, length = 100)
	private String localTime; //게시물 영업 시간

	@Column(nullable = false, length = 80)
	private String localAddress; // 게시물 주소

	@Column(nullable = false, length = 50)
	private String localPhone; // 게시물 전화번호

	@OneToMany(mappedBy = "generalPost")
	private List<GeneralPostImg> generalpostImgs = new ArrayList<>();

	@Builder
	public GeneralPost(Long localId, String localName, String localMoneyDescription, String localWeb, String localTime,
		String localAddress, String localPhone) {
		this.localId = localId;
		this.localName = localName;
		this.localMoneyDescription = localMoneyDescription;
		this.localWeb = localWeb;
		this.localTime = localTime;
		this.localAddress = localAddress;
		this.localPhone = localPhone;
	}


}
