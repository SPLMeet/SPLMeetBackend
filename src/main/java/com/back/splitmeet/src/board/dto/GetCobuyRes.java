package com.back.splitmeet.src.board.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCobuyRes {
	private String localName;
	private String localPlace;
	private List<String> localPhoto;
	private Long localMoney;
	private String localDescription;
	private Long limitPeople;
	private Long nowPeople;
}
