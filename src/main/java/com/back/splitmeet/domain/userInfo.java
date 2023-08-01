package com.back.splitmeet.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class userInfo {
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long userId;

	private Long teamId;

	private LocalDateTime createAt;

	private LocalDateTime updateAt;

	private UserStatus status;

	private String userName;

	private String userProfile;

	private String userEmail;

	private Integer role;

	private Integer submitMoney;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team")
	private userTeam userteam;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "payListId")
	private List<payList> orders = new ArrayList<>();
}
