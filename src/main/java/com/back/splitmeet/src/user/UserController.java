package com.back.splitmeet.src.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.domain.UserInfo;
import com.back.splitmeet.domain.repository.UserInfoRepository;
import com.back.splitmeet.src.auth.AuthService;
import com.back.splitmeet.src.user.dto.GetMemberToIdtoken;
import com.back.splitmeet.src.user.dto.GetUserInfoRes;
import com.back.splitmeet.src.user.dto.KakaoLoginReq;
import com.back.splitmeet.src.user.dto.KakaoLoginRes;
import com.back.splitmeet.util.BaseResponse;
import com.back.splitmeet.util.BaseResponseStatus;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
	private UserService userService;
	private AuthService authService;
	private UserInfoRepository userInfoRepository;

	@Autowired
	public UserController(UserService userService, AuthService authService, UserInfoRepository userInfoRepository) {
		this.userService = userService;
		this.authService = authService;
		this.userInfoRepository = userInfoRepository;
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
		KakaoLoginRes kakaoLoginRes = userService.KakaoService(getMemberToIdtoken, action);
		if (kakaoLoginRes == null) {
			return null;
		} else {
			return new BaseResponse<>(kakaoLoginRes);
		}
	}

	@GetMapping("/{userid}")
	public BaseResponse<GetUserInfoRes> getUserInfo(@PathVariable("userid") Integer userId) {
		UserInfo user = userInfoRepository.findOneByUserId(userId);
		GetUserInfoRes getUserInfoRes = new GetUserInfoRes(user.getUserProfile());
		return new BaseResponse<>(getUserInfoRes);
	}
}
