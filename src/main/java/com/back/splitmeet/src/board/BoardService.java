package com.back.splitmeet.src.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.CoBuyPostImg;
import com.back.splitmeet.domain.GeneralPostImg;
import com.back.splitmeet.domain.repository.CoBuyPostImgRepository;
import com.back.splitmeet.domain.repository.CoBuyPostRepository;
import com.back.splitmeet.domain.repository.GeneralPostImgRepository;
import com.back.splitmeet.domain.repository.GeneralPostRepository;
import com.back.splitmeet.src.board.dto.GetBoardRes;
import com.back.splitmeet.src.board.dto.GetBoardTemplete;

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
			GeneralPostImg generalPostImg = generalPostImgRepository.findByLocalId(generalPost.getLocalId());
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
			CoBuyPostImg coBuyPostImg = coBuyPostImgRepository.findByLocalId(generalPost.getLocalId());
			String url;
			if (coBuyPostImg == null)
				url = null;
			else
				url = coBuyPostImg.getImgUrl();
			return GetBoardTemplete.builder()
				.localId(generalPost.getLocalId())
				.localPhoto(url)
				.localPlace(generalPost.getLocalPlace())
				.localName(generalPost.getLocalName())
				.build();
		}).toList();
		// TODO: 추후 자리 기준으로 변경
		List<GetBoardTemplete> seatList = coBuyPostRepository.findAll().stream().map(generalPost -> {
			CoBuyPostImg coBuyPostImg = coBuyPostImgRepository.findByLocalId(generalPost.getLocalId());
			String url;
			if (coBuyPostImg == null)
				url = null;
			else
				url = coBuyPostImg.getImgUrl();
			return GetBoardTemplete.builder()
				.localId(generalPost.getLocalId())
				.localPhoto(url)
				.localPlace(generalPost.getLocalPlace())
				.localName(generalPost.getLocalName())
				.build();
		}).toList();
		return new GetBoardRes(localList, timeList, seatList);
	}
}
