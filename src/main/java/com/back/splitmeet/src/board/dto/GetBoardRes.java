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
	// 공통
	Long localId;
	String localName;
	String localAddress;
	List<String> localPhoto;
	// CoBuyPost
	LocalDateTime timeLimit;

}
