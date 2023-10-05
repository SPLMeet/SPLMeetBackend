package com.back.splitmeet.src.board;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.CoBuyPostImg;
import com.back.splitmeet.domain.GeneralPostImg;
import com.back.splitmeet.domain.repository.CoBuyPostRepository;
import com.back.splitmeet.domain.repository.GeneralPostRepository;
import com.back.splitmeet.src.board.dto.GetBoardRes;
import com.back.splitmeet.src.board.dto.GetBoardsRes;

@Service
public class BoardService {
	@Autowired
	private GeneralPostRepository generalPostRepository;
	@Autowired
	private CoBuyPostRepository coBuyPostRepository;

	public GetBoardsRes boardList(String title) {
		if (Objects.equals(title, "government")) {
			List<GetBoardRes> board = generalPostRepository.findAll().stream().map(
				generalPost -> GetBoardRes.builder()
					.localId(generalPost.getLocalId())
					.localPhoto(generalPost.getGeneralpostImgs().stream().map(GeneralPostImg::getImgUrl).toList())
					.localAddress(generalPost.getLocalAddress())
					.localName(generalPost.getLocalName())
					.build()
			).toList();
			return new GetBoardsRes("지자체", board);
		}
		if (Objects.equals(title, "time")) {
			List<GetBoardRes> board = coBuyPostRepository.findAllByOrderByTimeLimit().stream().map(
				coBuyPost -> GetBoardRes.builder()
					.localId(coBuyPost.getIdx())
					.localPhoto(coBuyPost.getCobuypostImgs().stream().map(CoBuyPostImg::getImgUrl).toList())
					.localAddress(coBuyPost.getLocalAddress())
					.localName(coBuyPost.getLocalName())
					.timeLimit(coBuyPost.getTimeLimit())
					.build()
			).toList();
			return new GetBoardsRes("기한 임박", board);
		}
		if (Objects.equals(title, "seat")) {
			List<GetBoardRes> board = coBuyPostRepository.findAllByOrderByTargetNumber().stream().map(
				coBuyPost -> GetBoardRes.builder()
					.localId(coBuyPost.getIdx())
					.localPhoto(coBuyPost.getCobuypostImgs().stream().map(CoBuyPostImg::getImgUrl).toList())
					.localAddress(coBuyPost.getLocalAddress())
					.localName(coBuyPost.getLocalName())
					.timeLimit(coBuyPost.getTimeLimit())
					.build()
			).toList();
			return new GetBoardsRes("자리 임박", board);
		}
		return null;
	}

	public GetBoardRes boardDetail(Long id) {
		return null; //수정 중
	}
}
