package com.back.splitmeet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "coBuyPostImg")
@NoArgsConstructor
public class CoBuyPostImg {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long postImgId; // 게시물 이미지 Id

	@Column(nullable = false, columnDefinition = "TEXT")
	private String imgUrl; // 게시물 이미지

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idx")
	private CoBuyPost coBuyPost;    // 게시물 이미지가 속한 게시물

	@Builder
	public CoBuyPostImg(String imgUrl, CoBuyPost coBuyPost) {
		this.imgUrl = imgUrl;
		this.coBuyPost = coBuyPost;
	}
}
