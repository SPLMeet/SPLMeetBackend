package com.back.splitmeet.src.split;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.src.split.dto.SplitCheckRes;
import com.back.splitmeet.util.BaseResponse;
import com.back.splitmeet.util.BaseResponseStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/split")
public class SplitController {
	private final SplitService splitService;

	@GetMapping("/check")
	public BaseResponse<List<SplitCheckRes>> checkSplit(@RequestHeader("Authorization") String accessToken) {
		List<SplitCheckRes> splitchklist = splitService.checkSplit(accessToken);
		return splitchklist == null ?
			new BaseResponse<>(BaseResponseStatus.INVALID_AUTH) :
			new BaseResponse<>(splitchklist);
	}
}