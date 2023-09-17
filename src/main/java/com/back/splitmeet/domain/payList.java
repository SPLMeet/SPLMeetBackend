package com.back.splitmeet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payList")
@Getter
@Setter
public class payList {
	@Id
	@Column
	private Long tid; // 결제 고유번호

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private UserInfo userinfo; // 유저 정보

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "localId")
	private CoBuyPost coBuyPost; // 게시물 정보

	private Long status; // 결제 상태 : 0(결제 전), 1(결제 중), 2(결제 완료) ,3(결제 취소)

	@Column(nullable = false)
	private Long personCount; // 티켓 갯수
}
