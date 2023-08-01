package com.back.splitmeet.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.splitmeet.domain.user.userInfo;

public interface userInfoRepository extends JpaRepository<userInfo, Long> {
	Optional<userInfo> findByEmail(String email);
}
