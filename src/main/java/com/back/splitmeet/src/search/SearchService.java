package com.back.splitmeet.src.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.CoBuyPost;
import com.back.splitmeet.domain.GeneralPost;
import com.back.splitmeet.domain.repository.CoBuyPostImgRepository;
import com.back.splitmeet.domain.repository.CoBuyPostRepository;
import com.back.splitmeet.domain.repository.GeneralPostRepository;
import com.back.splitmeet.jwt.JwtTokenProvider;
import com.back.splitmeet.src.search.dto.SearchRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
	private final JwtTokenProvider jwtTokenProvider;
	private final CoBuyPostRepository coBuyPostRepository;
	private final CoBuyPostImgRepository coBuyPostImgRepository;
	private final GeneralPostRepository generalPostRepository;
	private final CoBuyPostImgRepository generalPostImgRepository;

	public List<SearchRes> searchPlace(String keyword) {

		List<SearchRes> searchRes = new ArrayList<>();

		List<CoBuyPost> coBuyPosts = coBuyPostRepository.findALLByLocalNameContaining(keyword);

		if(coBuyPosts != null) {
			for (CoBuyPost e : coBuyPosts) {
				searchRes.add(
					new SearchRes(0L, e.getIdx(), e.getLocalName(), e.getLocalAddress(),
						e.getCobuypostImgs().get(0).getImgUrl(), e.getLocalMoney()));
			}
		}

		List<GeneralPost> generalPosts = generalPostRepository.findALLByLocalNameContaining(keyword);

		if(generalPosts != null) {
			for (GeneralPost e : generalPosts) {
				searchRes.add(
					new SearchRes(1L, e.getLocalId(), e.getLocalName(), e.getLocalAddress(),
						e.getGeneralpostImgs().get(0).getImgUrl(), null));
			}
		}

		return searchRes;
	}
}
