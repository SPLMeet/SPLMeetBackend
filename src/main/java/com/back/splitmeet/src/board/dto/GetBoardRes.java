package com.back.splitmeet.src.board.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetBoardRes {
	List<GetBoardTemplete> localList;
	List<GetBoardTemplete> timeList;
	List<GetBoardTemplete> seatList;
}
