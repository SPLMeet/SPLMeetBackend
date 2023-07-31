package com.back.splitmeet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "generalPostImg")
@Getter
@Setter
public class generalPostImg {
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long postImgId;

	private String imgUrl;

}
