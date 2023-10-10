package com.back.splitmeet.src.user.dto;

import com.back.splitmeet.domain.CoBuyPost;
import com.back.splitmeet.domain.PayList;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetReceiptRes {
	private PayList payList;
}
