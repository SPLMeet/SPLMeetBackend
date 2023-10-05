package com.back.splitmeet.src.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.domain.repository.UserInfoRepository;
import com.back.splitmeet.src.auth.AuthService;
import com.back.splitmeet.src.user.dto.GetMemberToIdtoken;
import com.back.splitmeet.src.user.dto.GetReceiptRes;
import com.back.splitmeet.src.user.dto.GetUserInfoRes;
import com.back.splitmeet.src.user.dto.KakaoLoginReq;
import com.back.splitmeet.src.user.dto.KakaoLoginRes;
import com.back.splitmeet.src.user.dto.SearchUserInfoRes;
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

		if (getMemberToIdtoken == null) {
			return new BaseResponse<>(BaseResponseStatus.INVALID_TOKEN);
		}
		KakaoLoginRes kakaoLoginRes = userService.kakaoLogin(getMemberToIdtoken, action);
		if ((kakaoLoginRes.getAccessToken() == null || kakaoLoginRes.getRefreshToken() == null) && action.equals(
			"signin")) {
			return new BaseResponse<>(BaseResponseStatus.NOT_SIGNED);
		} else if ((kakaoLoginRes.getAccessToken() == null || kakaoLoginRes.getRefreshToken() == null) && action.equals(
			"signup")) {
			return new BaseResponse<>(BaseResponseStatus.POST_USERS_EXISTS_EMAIL);
		} else {
			return new BaseResponse<>(kakaoLoginRes);
		}
	}

	/**
	 * 유저 정보 조회 API
	 * 토큰 없이도 조회 가능
	 * @param accessToken
	 * @return
	 */
	// @GetMapping("/{userid}")
	// public BaseResponse<GetUserInfoRes> getUserInfo(@PathVariable("userid") Long userId) {
	// 	UserInfo user = userInfoRepository.findOneByUserId(userId);
	// 	if (user == null) {
	// 		return new BaseResponse<>(BaseResponseStatus.INVALID_AUTH);
	// 	}
	// 	GetUserInfoRes getUserInfoRes = new GetUserInfoRes(user.getUserProfile());
	// 	return new BaseResponse<>(getUserInfoRes);
	// }
	@GetMapping("/logout")
	public BaseResponse<String> userLogout(@RequestHeader(value = "Authorization") String accessToken) {
		return new BaseResponse<>(userService.userLogout(accessToken));
	}

	@DeleteMapping("/{userid}")
	public BaseResponse<GetUserInfoRes> userDelete(@PathVariable("userid") Long userId,
		@RequestHeader("Authorization") String accessToken) {
		return new BaseResponse<>(userService.userDelete(userId, accessToken));
	}

	@GetMapping("/receipt/{userId}")
	public BaseResponse<List<GetReceiptRes>> getReceipt(@RequestHeader("Authorization") String accessToken,
		@PathVariable("userId") Long userId) {
		List<GetReceiptRes> ResceiptList = userService.getReceipt(accessToken, userId);
		return ResceiptList == null ?
			new BaseResponse<>(BaseResponseStatus.INVALID_AUTH) :
			new BaseResponse<>(ResceiptList);
	}

	@GetMapping("/userInfo")
	public BaseResponse<SearchUserInfoRes> getUserInfomat(@RequestHeader(value = "Authorization") String accessToken) {
		return new BaseResponse<>(userService.getUserInfo(accessToken));
	}
}
