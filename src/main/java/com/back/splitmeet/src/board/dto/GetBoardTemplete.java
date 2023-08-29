package com.back.splitmeet.src.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class GetBoardTemplete {
	String localName;
	String localPlace;
	String localPhoto;
	Long localId;
}
