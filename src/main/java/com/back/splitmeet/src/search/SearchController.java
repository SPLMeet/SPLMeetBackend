package com.back.splitmeet.src.search;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.src.search.dto.SearchRes;
import com.back.splitmeet.util.BaseResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {
	private final SearchService searchService;

	@GetMapping
	public BaseResponse<List<SearchRes>> search(@RequestHeader("Authorization") String accessToken,
		@RequestParam("keyword") String keyword) {
		List<SearchRes> searchRes = searchService.searchPlace(accessToken, keyword);
		return new BaseResponse<>(searchRes);
	}

}
