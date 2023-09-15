package com.back.splitmeet.src.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class GetBoardRes {
	Long localMoney;
	String localName;
	String localDescription;
	String localAddress;
	Long targetNumber;
	Long status;
	LocalDateTime timeLimit;
	List<String> localPhoto;
	Long localId;
}
