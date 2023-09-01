package com.back.splitmeet.src.scheduling.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ScheduleModifyReq {
	@JsonProperty(value = "teamId")
	private Long teamId;

	@JsonProperty(value = "date")
	private String date;

	@JsonProperty(value = "time")
	private String time;

	@JsonProperty(value = "place")
	private String place;

	@JsonProperty(value = "cost")
	private Integer cost;
}