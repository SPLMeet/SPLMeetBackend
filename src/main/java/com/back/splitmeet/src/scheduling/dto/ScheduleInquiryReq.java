package com.back.splitmeet.src.scheduling.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ScheduleInquiryReq {

	@JsonProperty(value = "Authorization")
	private String accessToken;
}
