package com.back.splitmeet.src.scheduling.dto;

import java.util.List;

import com.back.splitmeet.domain.Schedule;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ScheduleInquiryRes {

	private Long teamId;

	private String teamName;

	@JsonProperty(value = "schedule")
	private List<ScheduleInquiryInfo> scheduleInquiryInfo;
}
