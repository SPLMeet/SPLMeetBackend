package com.back.splitmeet.src.board.dto;

import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class PostCreateReq {
	@NonNull
	private String postType;
	@NonNull
	private String localName;
	@NonNull
	private String localPlace;

	private List<String> localPhoto;

	//general
	private String localNumber;
	private String localTime;
	private String localWeb;
	private String localMoneyDescription;
	private String localAddress;
	//cobuy
	private Long localMoney;
	private String localDescription;
}
