package com.back.splitmeet.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.splitmeet.domain.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	UserInfo findOneByUserId(Long userId);

	UserInfo findOneByUserEmailAndUserName(String email, String nickname);

	boolean existsByRefreshToken(String refreshToken);

	UserInfo findByUserEmail(String userEmail);

	UserInfo findOneByUserEmail(String userEmail);

	List<UserInfo> findAllByTeamId(Long teamId);
}
