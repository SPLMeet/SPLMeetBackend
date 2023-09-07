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

	@JsonProperty(value = "team_id")
	private Long teamId;

	private ZonedDateTime date;

	private String place;

	private Integer cost;

	private Boolean modifyOption;  //True면 수정 False면 추가
}