package com.back.splitmeet.src.split.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SplitStatusRes {
	private String url;
	private Boolean status;
	private Boolean isLeader;
}
