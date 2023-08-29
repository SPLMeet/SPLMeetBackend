package com.back.splitmeet.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.splitmeet.domain.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

	UserInfo findByUserEmailAndUserName(String email, String name);

	UserInfo findOneByUserId(Integer userId);
}
