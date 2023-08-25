package com.back.splitmeet.src.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.src.auth.AuthService;
import com.back.splitmeet.src.user.dto.GetMemberToIdtoken;
import com.back.splitmeet.src.user.dto.GetUserInfoRes;
import com.back.splitmeet.src.user.dto.KakaoLoginReq;
import com.back.splitmeet.src.user.dto.KakaoLoginRes;
import com.back.splitmeet.util.BaseResponse;
import com.back.splitmeet.util.BaseResponseStatus;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	private UserService userService;
	private AuthService authService;

	@Autowired
	public UserController(UserService userService, AuthService authService) {
		this.userService = userService;
		this.authService = authService;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public BaseResponse<MethodArgumentNotValidException> handleValidationExceptions(
		MethodArgumentNotValidException ex) {
		return new BaseResponse<>(ex);
	}

	@SneakyThrows
	@GetMapping("/sign")
	public BaseResponse<KakaoLoginRes> kakaoSign(@RequestParam(value = "idtoken") KakaoLoginReq idToken,
		@RequestParam(value = "action") String action) {
		if (idToken == null) {
			return new BaseResponse<>(BaseResponseStatus.EMPTY_TOKEN);
		}

		String decodedInfo = authService.decryptBase64UrlToken(idToken.getIdToken().split("\\.")[1]);
		GetMemberToIdtoken getMemberToIdtoken = userService.tranJsonToGetMemberTo(decodedInfo);

		if (getMemberToIdtoken.getEmail() == null || getMemberToIdtoken.getNickname() == null
			|| getMemberToIdtoken.getPicture() == null) {
			return new BaseResponse<>(BaseResponseStatus.INVALID_TOKEN);
		}
		return new BaseResponse<>(userService.KakaoService(getMemberToIdtoken, action));
	}

	@GetMapping("/{userid}")
	public BaseResponse<GetUserInfoRes> getUserInfo(@PathVariable("userid") Integer userId,
		@RequestHeader(value = "Authorization") String accessToken) {
		GetUserInfoRes getUserInfoRes = new GetUserInfoRes();
		return new BaseResponse<>(getUserInfoRes);
	}
}
