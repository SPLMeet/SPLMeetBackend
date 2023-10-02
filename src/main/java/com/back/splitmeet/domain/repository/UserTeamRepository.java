package com.back.splitmeet.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.splitmeet.domain.UserTeam;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {

	UserTeam findOneByTeamId(Long teamId);

}
