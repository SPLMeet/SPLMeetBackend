package com.back.splitmeet.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserTeam {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long teamId; // 팀 아이디

	@Column(nullable = false, length = 320) // , name = "userteam_name" 옵션을 줄 수 있음
	private String teamName; // 팀 이름

	@Column(nullable = false)
	private Long teamLeader; // 팀장 아이디 (userId)

	@Column(nullable = false, columnDefinition = "boolean default false")
	private Boolean teamSettleStatus; // 팀 정산 여부

	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDate startDate; // 팀 시작 날짜

	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDate endDate; // 팀 종료 날짜

	private Long teamTotalCost; // 팀 총 비용

	@OneToMany(mappedBy = "userTeam", cascade = CascadeType.ALL)
	private List<UserInfo> userInfo = new ArrayList<>();

	@Column(length = 20, columnDefinition = "VARCHAR(20) default 'NONE'")
	private String leaderKakaoHash; // 팀장 카카오 해시태그

	@Builder
	public UserTeam(Long teamLeader, String teamName) {
		this.teamLeader = teamLeader;
		this.teamName = teamName;
	}

	@PrePersist
	public void prePersist() {

		this.teamName = this.teamName == null ? "NONE" : this.teamName;
		this.teamLeader = this.teamLeader == null ? 0L : this.teamLeader;
		this.teamSettleStatus = this.teamSettleStatus == null ? false : this.teamSettleStatus;
		this.startDate = this.startDate == null ? LocalDate.now() : this.startDate;
		this.endDate = this.endDate == null ? LocalDate.now() : this.endDate;
		this.teamTotalCost = this.teamTotalCost == null ? 0L : this.teamTotalCost;
	}

}
