package com.back.splitmeet.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId; // 유저 Id

	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createAt; // 유저 생성 시간

	private LocalDateTime updateAt; // 유저 업데이트 시간

	@Column(nullable = false, columnDefinition = "VARCHAR(10) default 'N'")
	@Enumerated(EnumType.STRING)
	private UserStatus status; // 유저 상태

	@Column(nullable = false, length = 45)
	private String userName; // 유저 이름

	@Column(nullable = false, columnDefinition = "TEXT")
	private String userProfile; // 유저 프로필 사진

	@Column(nullable = false, length = 45)
	private String userEmail; // 유저 이메일

	@Column(nullable = false, columnDefinition = "VARCHAR(10) default 'NONE'")
	@Enumerated(EnumType.STRING)
	private RoleStatus role; // 유저 권한

	@Column(nullable = false, columnDefinition = "VARCHAR(10) default 'DONE'")
	@Enumerated(EnumType.STRING)
	private SubmitMoneyStatus submitMoney; // 유저 정산 여부

	@Column(columnDefinition = "TEXT")
	private String accessToken; // 유저 토큰

	@Column(columnDefinition = "TEXT")
	private String refreshToken; // 유저 토큰

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teamId")
	private UserTeam userTeam; // 유저 팀

	@OneToMany(mappedBy = "userinfo", cascade = CascadeType.ALL)
	@JsonBackReference
	private List<PayList> orders = new ArrayList<>(); // 유저 결제 내역

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

	@PrePersist
	public void prePersist() {

		this.createAt = this.createAt == null ? LocalDateTime.now() : this.createAt;
		this.role = this.role == null ? RoleStatus.NONE : this.role;
		this.status = this.status == null ? UserStatus.Maintain : this.status;
		this.submitMoney = this.submitMoney == null ? SubmitMoneyStatus.NONE : this.submitMoney;
	}
}
