package com.back.splitmeet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "coBuyPostImg")
public class CoBuyPostImg {
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long postImgId;

	private String imgUrl;
}
