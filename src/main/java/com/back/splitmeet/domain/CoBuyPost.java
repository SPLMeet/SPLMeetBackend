package com.back.splitmeet.domain;

import java.time.LocalDateTime;
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
@Table(name = "coBuyPost")
@Getter
@Setter
@NoArgsConstructor
public class CoBuyPost {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long idx;

	private Long localMoney;

	@Column(length = 50, nullable = false)
	private String localName;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String localDesription;

	@Column(length = 100, nullable = false)
	private String localAddress;

	@Column(nullable = false)
	private Long targetNumber;

	@Column(nullable = false)
	private Long status;

	@Column(nullable = false)
	private LocalDateTime timeLimit;

	@Builder
	public CoBuyPost(Long idx, Long localMoney, String localName, String localDesription, String localAddress) {
		this.idx = idx;
		this.localMoney = localMoney;
		this.localName = localName;
		this.localDesription = localDesription;
		this.localAddress = localAddress;
	}

	@OneToMany
	@JoinColumn(name = "localId")
	private List<CoBuyPostImg> coBuyPostImg;
}
