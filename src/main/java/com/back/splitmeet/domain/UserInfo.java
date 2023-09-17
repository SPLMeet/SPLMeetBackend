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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
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

	@Column(columnDefinition = "TEXT")
	private String accessToken;

	@Column(columnDefinition = "TEXT")
	private String refreshToken;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team")
	private UserTeam userteam;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "payListId")
	private List<payList> orders = new ArrayList<>();

	@Builder(builderMethodName = "userBuilder", buildMethodName = "userBuild")
	public UserInfo(String userEmail, String nickname, String userProfile) {
		this.userEmail = userEmail;
		this.userName = nickname;
		this.userProfile = userProfile;
	}

	@Builder(builderMethodName = "tokenBuilder", buildMethodName = "tokenBuild")
	public UserInfo(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public static UserInfo createUser(String userEmail, String nickname, String userProfile) {
		return new UserInfo(userEmail, nickname, userProfile);
	}
}
