package com.back.splitmeet.src.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.src.user.dto.PostUserTestReq;
import com.back.splitmeet.src.user.dto.PostUserTestRes;
import com.back.splitmeet.util.BaseResponse;
import com.back.splitmeet.util.BaseResponseStatus;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/test")
	public BaseResponse<PostUserTestRes> test(@RequestBody PostUserTestReq req) {
		PostUserTestRes postUserTestRes = userService.test(req);
		if (postUserTestRes.getUserIdx() == 1) {
			return new BaseResponse<>(BaseResponseStatus.EMPTY_JWT);
		}

		return new BaseResponse<>(postUserTestRes);
	}
}
