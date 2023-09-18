package com.back.splitmeet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private Long localId;
	@Column(nullable = false, columnDefinition = "TEXT")
	private String imgUrl; // 게시물 이미지
  
	@Builder
	public CoBuyPostImg(Long localId, String imgUrl) {
		this.localId = localId;
		this.imgUrl = imgUrl;
	}
}
