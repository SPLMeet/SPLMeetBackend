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
import com.back.splitmeet.src.scheduling.dto.ScheduleModifyInfo;
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
		for (ScheduleModifyInfo modifyInfo : req.getModifyList()) {
			Schedule schedule = scheduleInfoRepository.findOneByScheduleId(modifyInfo.getScheduleId());
			saveScheduleInRepository(modifyInfo, schedule);
		}
		return true;
	}

	public Boolean addSchedule(ScheduleAddReq req) {
		try {
			TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(req.getAccessToken());
			UserInfo userInfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());
			Long teamId = userInfo.getUserTeam().getTeamId();
			Schedule schedule = new Schedule();
			saveScheduleInRepository(req, schedule, teamId);
			return true;
		} catch (Exception e) {
			return null;
		}

	}

	@Transactional
	public void saveScheduleInRepository(ScheduleModifyInfo modifyInfo, Schedule schedule) {
		schedule.setStartTime(modifyInfo.getStartTime());
		schedule.setEndTime(modifyInfo.getEndTime());
		schedule.setPlace(modifyInfo.getPlace());
		schedule.setCost(modifyInfo.getCost());
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