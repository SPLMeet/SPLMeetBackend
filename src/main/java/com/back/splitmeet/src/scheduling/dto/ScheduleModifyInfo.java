package com.back.splitmeet.src.scheduling.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class ScheduleModifyInfo {
	private Long scheduleId;

	private ZonedDateTime startTime;

	private ZonedDateTime endTime;

	private String place;

	private Integer cost;
}
