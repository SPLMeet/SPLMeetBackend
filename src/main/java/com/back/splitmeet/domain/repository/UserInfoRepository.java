package com.back.splitmeet.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.splitmeet.domain.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
