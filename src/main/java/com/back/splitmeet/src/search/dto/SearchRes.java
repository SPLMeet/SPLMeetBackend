package com.back.splitmeet.src.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchRes {
	private Long Type;
	private Long idx;
	private String Name;
	private String Place;
	private String Photo;
	private Long Money;
}
