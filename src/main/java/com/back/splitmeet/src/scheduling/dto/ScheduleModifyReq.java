package com.back.splitmeet.src.scheduling.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleModifyReq {

	@JsonProperty(value = "team_id")
	private Long teamId;

	private String date;

	private String time;

	private String place;

	private Integer cost;
}