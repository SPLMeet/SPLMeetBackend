package com.back.splitmeet.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "generalPostImg")
@NoArgsConstructor
public class GeneralPostImg {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long postImgId;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String imgUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "localId")
	@JsonBackReference
	private GeneralPost generalPost;

	@Builder
	public GeneralPostImg(String imgUrl, GeneralPost generalPost) {
		this.imgUrl = imgUrl;
		this.generalPost = generalPost;
	}
}
