package com.back.splitmeet.src.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.jwt.JwtTokenProvider;
import com.back.splitmeet.src.team.dto.PostCreateTeamRes;
import com.back.splitmeet.util.BaseException;
import com.back.splitmeet.util.BaseResponse;
import com.back.splitmeet.util.BaseResponseStatus;

@RestController
@RequestMapping("/api/team")
public class TeamController {
	@Autowired
	private TeamService teamService;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public BaseResponse<MethodArgumentNotValidException> handleValidationExceptions(
		MethodArgumentNotValidException ex) {

		return new BaseResponse<>(ex);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public BaseResponse<HttpMessageNotReadableException> handleNotReadableException(
		HttpMessageNotReadableException ex) {
		return new BaseResponse<>(ex);
	}

	@PutMapping("/create")
	public BaseResponse<PostCreateTeamRes> createRoom(
		@RequestHeader("Authorization") String token) throws
		BaseException {
		//jwtTokenProvider.verifySignature(token);
		PostCreateTeamRes postCreateRoomRes = teamService.createRoom(token);
		if (postCreateRoomRes == null) {
			return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
		}
		if (postCreateRoomRes.getTeam_idx() == -1) {
			return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
		}
		if (postCreateRoomRes.getTeam_idx() == 0) {
			return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
		}
		return new BaseResponse<>(postCreateRoomRes);
	}

}
