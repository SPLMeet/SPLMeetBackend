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
@Table(name = "generalPost")
@Getter
@Setter
public class GeneralPost {
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long localId;

	private String localName;

	private String localMoneyDescription;

	private String localWeb;

	private String localTime;

	private String localAddress;

	private String localNum;

	@OneToMany
	@JoinColumn(name = "PostImg")
	private List<GeneralPostImg> generalpostImgs = new ArrayList<>();
}
