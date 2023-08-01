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
	private Long tid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private userInfo userinfo;

}
