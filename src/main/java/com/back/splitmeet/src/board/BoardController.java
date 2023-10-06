package com.back.splitmeet.src.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.src.board.dto.GetBoardsRes;
import com.back.splitmeet.src.board.dto.GetCobuyRes;
import com.back.splitmeet.util.BaseResponse;

@RestController
@RequestMapping("/api/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@GetMapping("/list")
	public BaseResponse<GetBoardsRes> boardList(@RequestParam("title") String title) {
		GetBoardsRes getBoardsRes = boardService.boardList(title);
		return new BaseResponse<>(getBoardsRes);
	}

	@GetMapping("/{id}")
	public BaseResponse<GetCobuyRes> boardDetail(@PathVariable("id") Long id) {
		return new BaseResponse<>(boardService.boardDetail(id));
	}
}
