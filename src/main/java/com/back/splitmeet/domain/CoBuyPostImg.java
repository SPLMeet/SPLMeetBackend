package com.back.splitmeet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
	@GeneratedValue
	@Column(nullable = false)
	private Long postImgId;
	private Long localId;
	private String imgUrl;

	@Builder
	public CoBuyPostImg(Long localId, String imgUrl) {
		this.localId = localId;
		this.imgUrl = imgUrl;
	}
}
