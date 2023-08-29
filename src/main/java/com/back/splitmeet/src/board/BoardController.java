package com.back.splitmeet.src.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.src.board.dto.GetBoardRes;
import com.back.splitmeet.src.board.dto.PostCreateReq;
import com.back.splitmeet.src.board.dto.PostCreateRes;
import com.back.splitmeet.util.BaseResponse;

@RestController
@RequestMapping("/api/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@GetMapping("/list")
	public BaseResponse<GetBoardRes> boardList() {
		GetBoardRes getBoardRes = boardService.boardList();
		return new BaseResponse<>(getBoardRes);
	}

	@PostMapping("/create")
	public BaseResponse<PostCreateRes> createBoard(@RequestBody PostCreateReq req) {
		PostCreateRes postCreateRes = boardService.createBoard(req);
		return new BaseResponse<>(postCreateRes);
	}
}
