package com.back.splitmeet.src.scheduling.dto;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class ScheduleAddReq {
	@JsonProperty(value = "Authorization")
	private String accessToken;

	private ZonedDateTime startTime;

	private ZonedDateTime endTime;

	private String place;

	private Integer cost;
}

