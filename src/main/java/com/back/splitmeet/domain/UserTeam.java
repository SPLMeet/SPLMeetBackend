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
	private Long teamId;

	private String teamName;

	private Long teamLeader;

	private Boolean teamSettleStatus;

	private LocalDate startDate;

	private LocalDate endDate;

	private Long teamTotalCost;

	private String leaderKakaoHash;

	@Builder
	public UserTeam(Long teamLeader, String teamName, String leaderKakaoHash) {
		this.teamLeader = teamLeader;
		this.teamName = teamName;
		this.leaderKakaoHash = leaderKakaoHash;
	}

}
