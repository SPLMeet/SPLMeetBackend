package com.back.splitmeet.src.scheduling.dto;

import java.time.ZonedDateTime;

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

	//@JsonProperty(value = "Authorization")
//	private String accessToken;

	@JsonProperty(value = "schedule_id")
	private Long scheduleId;

	private ZonedDateTime startTime;

	private ZonedDateTime endTime;

	private String place;

	private Integer cost;
}