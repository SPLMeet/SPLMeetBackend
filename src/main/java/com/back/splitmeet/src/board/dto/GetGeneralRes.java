package com.back.splitmeet.src.board.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * 일반게시물 디테일 조회
 *
 */
@Data
@Builder
public class GetGeneralRes {

	private String localName;
	private String localAddress;
	private List<String> localPhoto;
	private String localMoneyDescription;
	private String localWeb;
	private String localTime;
	private String localPhone;

}
