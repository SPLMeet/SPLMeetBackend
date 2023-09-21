package com.back.splitmeet.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.splitmeet.domain.UserTeam;
import com.back.splitmeet.src.split.dto.SplitCheckRes;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {

	UserTeam findOneByTeamId(Long teamId);

	List<SplitCheckRes> findALLByTeamId(Long teamId);
}
