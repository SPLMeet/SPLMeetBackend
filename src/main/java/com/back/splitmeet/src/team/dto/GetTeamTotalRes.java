package com.back.splitmeet.src.team.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GetTeamTotalRes {

	private String teamName;
	private LocalDate startDate;
	private LocalDate endDate;
	private Long teamTotalCost;
	private List<GetTeamMemberRes> teamMemberList;
}
