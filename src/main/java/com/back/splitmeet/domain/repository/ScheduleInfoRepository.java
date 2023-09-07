package com.back.splitmeet.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.splitmeet.domain.Schedule;

public interface ScheduleInfoRepository extends JpaRepository<Schedule, Long> {
	List<Schedule> findAllByTeamIdOrderByDate(Long teamId);

}
