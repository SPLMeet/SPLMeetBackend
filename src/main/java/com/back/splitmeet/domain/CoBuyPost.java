package com.back.splitmeet.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "coBuyPost")
@Getter
@Setter
@NoArgsConstructor
public class CoBuyPost {
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long localId;

	private Integer localMoney;

	private String localName;

	private String localDesription;

	private String localPlace;

	@Builder
	public CoBuyPost(Long localId, Integer localMoney, String localName, String localDesription, String localPlace) {
		this.localId = localId;
		this.localMoney = localMoney;
		this.localName = localName;
		this.localDesription = localDesription;
		this.localPlace = localPlace;
	}

	@OneToMany
	@JoinColumn(name = "PostImg")
	private List<CoBuyPostImg> cobuypostImgs = new ArrayList<>();
}
