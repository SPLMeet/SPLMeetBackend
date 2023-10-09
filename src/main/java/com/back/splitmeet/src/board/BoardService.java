package com.back.splitmeet.src.board;

import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.CoBuyPost;
import com.back.splitmeet.domain.CoBuyPostImg;
import com.back.splitmeet.domain.GeneralPost;
import com.back.splitmeet.domain.GeneralPostImg;
import com.back.splitmeet.domain.repository.CoBuyPostRepository;
import com.back.splitmeet.domain.repository.GeneralPostRepository;
import com.back.splitmeet.domain.repository.PayListRepository;
import com.back.splitmeet.src.board.dto.GetBoardRes;
import com.back.splitmeet.src.board.dto.GetBoardsRes;
import com.back.splitmeet.src.board.dto.GetCobuyRes;
import com.back.splitmeet.src.board.dto.GetGeneralRes;

@Service
public class BoardService {
	@Autowired
	private GeneralPostRepository generalPostRepository;
	@Autowired
	private CoBuyPostRepository coBuyPostRepository;
	@Autowired
	private PayListRepository payListRepository;

	public GetBoardsRes boardList(String title) {
		if (Objects.equals(title, "government")) {
			List<GetBoardRes> board = generalPostRepository.findAll().stream().map(
				generalPost -> {
					List<String> images = Optional.ofNullable(generalPost.getGeneralpostImgs())
						.orElse(Collections.emptyList())  // null 대신 빈 목록 반환
						.stream()
						.filter(Objects::nonNull)  // null 값 제거
						.map(GeneralPostImg::getImgUrl)
						.toList();
					String firstImage = images.isEmpty() ? null : images.get(0);
					return GetBoardRes.builder()
						.localId(generalPost.getLocalId())
						.localPhoto(firstImage)
						.localAddress(generalPost.getLocalAddress())
						.localName(generalPost.getLocalName())
						.build();
				}
			).toList();
			return new GetBoardsRes("지자체", board);
		}
		if (Objects.equals(title, "time")) {
			List<GetBoardRes> board = coBuyPostRepository.findAllByOrderByTimeLimit().stream().map(
				coBuyPost -> {
					List<String> images = Optional.ofNullable(coBuyPost.getCobuypostImgs())
						.orElse(Collections.emptyList())  // null 대신 빈 목록 반환
						.stream()
						.filter(Objects::nonNull)  // null 값 제거
						.map(CoBuyPostImg::getImgUrl)
						.toList();
					String firstImage = images.isEmpty() ? null : images.get(0);
					return GetBoardRes.builder()
						.localId(coBuyPost.getIdx())
						.localPhoto(firstImage)
						.localAddress(coBuyPost.getLocalAddress())
						.localName(coBuyPost.getLocalName())
						.timeLimit(Date.from(coBuyPost.getTimeLimit().atZone(ZoneId.systemDefault()).toInstant()))
						.build();
				}
			).toList();
			return new GetBoardsRes("기한 임박", board);
		}

		if (Objects.equals(title, "seat")) {
			List<GetBoardRes> board = coBuyPostRepository.findAllByOrderByTargetNumber().stream().map(
				coBuyPost -> {
					List<String> images = Optional.ofNullable(coBuyPost.getCobuypostImgs())
						.orElse(Collections.emptyList())  // null 대신 빈 목록 반환
						.stream()
						.filter(Objects::nonNull)  // null 값 제거
						.map(CoBuyPostImg::getImgUrl)
						.toList();
					String firstImage = images.isEmpty() ? null : images.get(0);
					return GetBoardRes.builder()
						.localId(coBuyPost.getIdx())
						.localPhoto(firstImage)
						.localAddress(coBuyPost.getLocalAddress())
						.localName(coBuyPost.getLocalName())
						.timeLimit(Date.from(coBuyPost.getTimeLimit().atZone(ZoneId.systemDefault()).toInstant()))
						.build();
				}
			).toList();
			return new GetBoardsRes("자리 임박", board);
		}
		return null;
	}

	public GetCobuyRes boardDetail(Long id) {
		CoBuyPost coBuyPost = coBuyPostRepository.findById(id).orElse(null);
		Integer nowPeople = payListRepository.sumPersonCount(id);
		if (coBuyPost == null) {
			return null;
		}
		return GetCobuyRes.builder()
			.localName(coBuyPost.getLocalName())
			.localPlace(coBuyPost.getLocalAddress())
			.localPhoto(coBuyPost.getCobuypostImgs().stream().map(CoBuyPostImg::getImgUrl).toList())
			.localMoney(coBuyPost.getLocalMoney())
			.localDescription(coBuyPost.getLocalDesription())
			.limitPeople(coBuyPost.getTargetNumber())
			.nowPeople(nowPeople.longValue())
			.build();
	}

	/**
	 * 일반게시물 디테일 조회
	 *
	 */
	public GetGeneralRes generalDetail(Long id) {
		GeneralPost generalPost = generalPostRepository.findById(id).orElse(null);
		if (generalPost == null) {
			return null;
		}
		return GetGeneralRes.builder()
			.localName(generalPost.getLocalName())
			.localAddress(generalPost.getLocalAddress())
			.localPhoto(generalPost.getGeneralpostImgs().stream().map(GeneralPostImg::getImgUrl).toList())
			.localMoneyDescription(generalPost.getLocalMoneyDescription())
			.localWeb(generalPost.getLocalWeb())
			.localTime(generalPost.getLocalTime())
			.localPhone(generalPost.getLocalPhone())
			.build();
	}
}
