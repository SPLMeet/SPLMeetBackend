package com.back.splitmeet.src.board;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.CoBuyPost;
import com.back.splitmeet.domain.CoBuyPostImg;
import com.back.splitmeet.domain.GeneralPost;
import com.back.splitmeet.domain.GeneralPostImg;
import com.back.splitmeet.domain.repository.CoBuyPostImgRepository;
import com.back.splitmeet.domain.repository.CoBuyPostRepository;
import com.back.splitmeet.domain.repository.GeneralPostImgRepository;
import com.back.splitmeet.domain.repository.GeneralPostRepository;
import com.back.splitmeet.src.board.dto.GetBoardRes;
import com.back.splitmeet.src.board.dto.GetBoardTemplete;
import com.back.splitmeet.src.board.dto.PostCreateReq;
import com.back.splitmeet.src.board.dto.PostCreateRes;

@Service
public class BoardService {
	@Autowired
	private GeneralPostRepository generalPostRepository;
	@Autowired
	private GeneralPostImgRepository generalPostImgRepository;
	@Autowired
	private CoBuyPostRepository coBuyPostRepository;
	@Autowired
	private CoBuyPostImgRepository coBuyPostImgRepository;

	public GetBoardRes boardList() {
		List<GetBoardTemplete> localList = generalPostRepository.findAll().stream().map(generalPost -> {
			GeneralPostImg generalPostImg = generalPostImgRepository.findTopByLocalId(generalPost.getLocalId());
			String url;
			if (generalPostImg == null)
				url = null;
			else
				url = generalPostImg.getImgUrl();
			return GetBoardTemplete.builder()
				.localId(generalPost.getLocalId())
				.localPhoto(url)
				.localPlace(generalPost.getLocalAddress())
				.localName(generalPost.getLocalName())
				.build();
		}).toList();
		// TODO: 추후 시간 기준으로 변경
		List<GetBoardTemplete> timeList = coBuyPostRepository.findAll().stream().map(generalPost -> {
			CoBuyPostImg coBuyPostImg = coBuyPostImgRepository.findTopByLocalId(generalPost.getLocalId());
			String url;
			if (coBuyPostImg == null)
				url = null;
			else
				url = coBuyPostImg.getImgUrl();
			return GetBoardTemplete.builder()
				.localId(generalPost.getLocalId())
				.localPhoto(url)
				.localPlace(generalPost.getLocalAddress())
				.localName(generalPost.getLocalName())
				.build();
		}).toList();
		// TODO: 추후 자리 기준으로 변경
		List<GetBoardTemplete> seatList = coBuyPostRepository.findAll().stream().map(generalPost -> {
			CoBuyPostImg coBuyPostImg = coBuyPostImgRepository.findTopByLocalId(generalPost.getLocalId());
			String url;
			if (coBuyPostImg == null)
				url = null;
			else
				url = coBuyPostImg.getImgUrl();
			return GetBoardTemplete.builder()
				.localId(generalPost.getLocalId())
				.localPhoto(url)
				.localPlace(generalPost.getLocalAddress())
				.localName(generalPost.getLocalName())
				.build();
		}).toList();
		return new GetBoardRes(localList, timeList, seatList);
	}

	public PostCreateRes createBoard(PostCreateReq req) {
		Long idx;
		if (req.getLocalPhoto() == null)
			req.setLocalPhoto(Collections.emptyList());
		if (req.getPostType().equals("cobuy")) {
			CoBuyPost coBuyPost = CoBuyPost.builder()
				.localDesription(req.getLocalDescription())
				.localMoney(req.getLocalMoney())
				.localName(req.getLocalName())
				.localAddress(req.getLocalAddress())
				.build();
			idx = coBuyPostRepository.save(coBuyPost).getLocalId();
			List<CoBuyPostImg> coBuyPostImgs = req.getLocalPhoto()
				.stream()
				.map(photo -> CoBuyPostImg.builder()
					.imgUrl(photo)
					.localId(idx)
					.build())
				.toList();
			coBuyPostImgRepository.saveAll(coBuyPostImgs);
		} else if (req.getPostType().equals("general")) {
			GeneralPost generalPost = GeneralPost.builder()
				.localAddress(req.getLocalAddress())
				.localWeb(req.getLocalWeb())
				.localTime(req.getLocalTime())
				.localNum(req.getLocalNumber())
				.localMoneyDescription(req.getLocalMoneyDescription())
				.localName(req.getLocalName())
				.build();
			idx = generalPostRepository.save(generalPost).getLocalId();
			List<GeneralPostImg> generalPostImgs = req.getLocalPhoto()
				.stream()
				.map(photo -> GeneralPostImg.builder()
					.imgUrl(photo)
					.localId(idx)
					.build())
				.toList();
			generalPostImgRepository.saveAll(generalPostImgs);
		} else
			idx = null;
		return new PostCreateRes(idx);
	}
}
