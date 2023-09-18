package com.back.splitmeet.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@Column(nullable = false)
	private LocalDate endDate; // 팀 종료 날짜

	private Long teamTotalCost; // 팀 총 비용

	@Column(length = 20, columnDefinition = "VARCHAR(20) default 'NONE'")
	private String leaderKakaoHash; // 팀장 카카오 해시태그

	@Builder
	public UserTeam(Long teamLeader, String teamName) {
		this.teamLeader = teamLeader;
		this.teamName = teamName;
	}

}
