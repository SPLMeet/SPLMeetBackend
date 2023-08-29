package com.back.splitmeet.domain.repository;

import com.back.splitmeet.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleInfoRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByTeamIdOrderByDate(Long teamId);



}
