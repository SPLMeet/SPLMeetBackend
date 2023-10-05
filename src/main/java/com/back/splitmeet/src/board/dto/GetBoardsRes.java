package com.back.splitmeet.src.board.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetBoardsRes {
	String title;
	List<GetBoardRes> childList;
}
