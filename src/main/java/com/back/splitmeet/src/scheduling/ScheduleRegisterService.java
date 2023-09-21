package com.back.splitmeet.src.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.Schedule;
import com.back.splitmeet.domain.UserInfo;
import com.back.splitmeet.domain.repository.ScheduleInfoRepository;
import com.back.splitmeet.domain.repository.UserInfoRepository;
import com.back.splitmeet.jwt.JwtTokenProvider;
import com.back.splitmeet.jwt.dto.TokenInfo;
import com.back.splitmeet.src.scheduling.dto.ScheduleAddReq;
import com.back.splitmeet.src.scheduling.dto.ScheduleModifyReq;

import jakarta.transaction.Transactional;

@Service
public class ScheduleRegisterService {
	@Autowired
	private ScheduleInfoRepository scheduleInfoRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserInfoRepository userInfoRepository;

	public ScheduleRegisterService(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public Boolean modifySchedule(ScheduleModifyReq req) {
		Schedule schedule = scheduleInfoRepository.findOneByScheduleId(req.getScheduleId());

		if (schedule.getCost() == req.getCost() && schedule.getPlace() == req.getPlace()
			&& schedule.getEndTime() == req.getEndTime() && schedule.getStartTime() == req.getStartTime()) {
			return null;
		}
		saveScheduleInRepository(req, schedule);
		return true;
	}

	public Boolean addSchedule(ScheduleAddReq req) {
		try {
			TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(req.getAccessToken());
			UserInfo userInfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());
			Long teamId = userInfo.getTeamId();
			Schedule schedule = new Schedule();
			saveScheduleInRepository(req, schedule, teamId);
			return true;
		} catch (Exception e) {
			return null;
		}

	}

	@Transactional
	public void saveScheduleInRepository(ScheduleModifyReq req, Schedule schedule) {
		//같은 값은 set 안하도록
		schedule.setStartTime(req.getStartTime());
		schedule.setEndTime(req.getEndTime());
		schedule.setPlace(req.getPlace());
		schedule.setCost(req.getCost());
		scheduleInfoRepository.save(schedule);
	}

	@Transactional
	public void saveScheduleInRepository(ScheduleAddReq req, Schedule schedule, Long teamId) {
		schedule.setStartTime(req.getStartTime());
		schedule.setEndTime(req.getEndTime());
		schedule.setPlace(req.getPlace());
		schedule.setCost(req.getCost());
		schedule.setTeamId(teamId);
		scheduleInfoRepository.save(schedule);
	}
}