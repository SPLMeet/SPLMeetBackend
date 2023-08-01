package com.back.splitmeet.domain.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.back.splitmeet.domain.Pay.payList;
import com.back.splitmeet.service.user.KakaoApiClient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

	private KakaoApiClient.OAuthProvider oAuthProvider;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team")
	private userTeam userteam;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "payListId")
	private List<payList> orders = new ArrayList<>();

	@Builder
	public userInfo(String userEmail, String nickname, KakaoApiClient.OAuthProvider oAuthProvider) {
		this.userEmail = userEmail;
		this.userName = nickname;
		this.oAuthProvider = oAuthProvider;
	}
}
