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
	private Long localId;

	private Integer localMoney;

	private String localName;

	private String localDesription;

	private String localPlace;

	@OneToMany
	@JoinColumn(name = "PostImg")
	private List<CoBuyPostImg> cobuypostImgs = new ArrayList<>();
}
