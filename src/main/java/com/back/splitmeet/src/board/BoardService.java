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
import com.back.splitmeet.src.board.dto.GetBoardsRes;
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

	public GetBoardsRes boardList() {
		List<GetBoardRes> localList = generalPostRepository.findAll().stream().map(generalPost -> {
			List<String> generalUrls = generalPost.getGeneralpostImgs().stream()
				.map(GeneralPostImg::getImgUrl)
				.toList();

			return GetBoardRes.builder()
				.localId(generalPost.getLocalId())
				.localPhoto(generalUrls)
				.localAddress(generalPost.getLocalAddress())
				.localName(generalPost.getLocalName())
				.build();
		}).toList();
		// TODO: 추후 시간 기준으로 변경
		List<GetBoardRes> timeList = coBuyPostRepository.findAll().stream().map(coBuyPost -> {
			List<String> coBuyPostTimeUrls = coBuyPost.getCobuypostImgs().stream()
				.map(CoBuyPostImg::getImgUrl)
				.toList();

			return GetBoardRes.builder()
				.localId(coBuyPost.getIdx())
				.localPhoto(coBuyPostTimeUrls)
				.localAddress(coBuyPost.getLocalAddress())
				.localName(coBuyPost.getLocalName())
				.build();
		}).toList();
		// TODO: 추후 자리 기준으로 변경
		List<GetBoardRes> seatList = coBuyPostRepository.findAll().stream().map(coBuyPost -> {
			List<String> coBuyPostSeatUrls = coBuyPost.getCobuypostImgs().stream()
				.map(CoBuyPostImg::getImgUrl)
				.toList();
			return GetBoardRes.builder()
				.localId(coBuyPost.getIdx())
				.localPhoto(coBuyPostSeatUrls)
				.localAddress(coBuyPost.getLocalAddress())
				.localName(coBuyPost.getLocalName())
				.build();
		}).toList();
		return new GetBoardsRes(localList, timeList, seatList);
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
			final CoBuyPost savedCoBuyPost = coBuyPostRepository.save(coBuyPost);
			List<CoBuyPostImg> coBuyPostImgs = req.getLocalPhoto()
				.stream()
				.map(photo -> CoBuyPostImg.builder()
					.imgUrl(photo)
					.coBuyPost(savedCoBuyPost)
					.build())
				.toList();
			coBuyPostImgRepository.saveAll(coBuyPostImgs);
			idx = savedCoBuyPost.getIdx();
		} else if (req.getPostType().equals("general")) {
			GeneralPost generalPost = GeneralPost.builder()
				.localAddress(req.getLocalAddress())
				.localWeb(req.getLocalWeb())
				.localTime(req.getLocalTime())
				.localPhone(req.getLocalNumber())
				.localMoneyDescription(req.getLocalMoneyDescription())
				.localName(req.getLocalName())
				.build();
			final GeneralPost savedGeneralPost = generalPostRepository.save(generalPost);
			List<GeneralPostImg> generalPostImgs = req.getLocalPhoto()
				.stream()
				.map(photo -> GeneralPostImg.builder()
					.imgUrl(photo)
					.generalPost(savedGeneralPost)
					.build())
				.toList();
			generalPostImgRepository.saveAll(generalPostImgs);
			idx = savedGeneralPost.getLocalId();
		} else
			idx = null;
		return new PostCreateRes(idx);
	}

	public GetBoardRes boardDetail(Long id) {
		CoBuyPost coBuyPost = coBuyPostRepository.findById(id).orElse(null);
		if (coBuyPost == null)
			return null;

		// coBuyPost 객체에서 직접 이미지를 가져옵니다.
		List<CoBuyPostImg> coBuyPostImgs = coBuyPost.getCobuypostImgs();
		// 이미지 URL 목록을 생성합니다.
		List<String> imgUrls = coBuyPostImgs.stream()
			.map(CoBuyPostImg::getImgUrl)
			.toList();
		return GetBoardRes.builder()
			.localId(coBuyPost.getIdx())
			.localMoney(coBuyPost.getLocalMoney())
			.localName(coBuyPost.getLocalName())
			.localDescription(coBuyPost.getLocalDesription())
			.localAddress(coBuyPost.getLocalAddress())
			.targetNumber(coBuyPost.getTargetNumber())
			.status(coBuyPost.getStatus())
			.timeLimit(coBuyPost.getTimeLimit())
			.localPhoto(imgUrls)
			.build();
	}
}
