package com.back.splitmeet.src.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.repository.UserInfoRepository;
import com.back.splitmeet.src.user.dto.PostUserTestReq;
import com.back.splitmeet.src.user.dto.PostUserTestRes;

@Service
public class UserService {
	@Autowired
	private UserInfoRepository userInfoRepository;

	public PostUserTestRes test(PostUserTestReq req) {
		Integer userNameLength = req.getUserName().length();
		return new PostUserTestRes(userNameLength);
	}
}
