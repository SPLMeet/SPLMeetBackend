package com.back.splitmeet.src.scheduling.dto;

import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScheduleInquiryInfo {

	private Long scheduleId;

	private ZonedDateTime startTime;

	private ZonedDateTime endTime;

	private String place;

	private Integer cost;
}