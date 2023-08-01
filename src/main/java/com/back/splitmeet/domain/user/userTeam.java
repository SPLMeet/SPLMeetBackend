package com.back.splitmeet.domain.user;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class userTeam {

	@Id
	@GeneratedValue
	@Column
	private Long teamId;

	private String teamName;

	private Long teamLeader;

	private Boolean teamSettleStatus;

	private LocalDate startDate;

	private LocalDate endDate;

	private Long teamTotalCost;

}
