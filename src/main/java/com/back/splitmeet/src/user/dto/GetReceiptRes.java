package com.back.splitmeet.src.user.dto;

import com.back.splitmeet.domain.CoBuyPost;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetReceiptRes {
	private CoBuyPost coBuyPost;
	private Long status;
}
