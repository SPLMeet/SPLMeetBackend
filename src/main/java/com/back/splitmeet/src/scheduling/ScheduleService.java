package com.back.splitmeet.src.scheduling;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.Schedule;
import com.back.splitmeet.domain.UserInfo;
import com.back.splitmeet.domain.UserTeam;
import com.back.splitmeet.domain.repository.ScheduleInfoRepository;
import com.back.splitmeet.domain.repository.UserInfoRepository;
import com.back.splitmeet.jwt.JwtTokenProvider;
import com.back.splitmeet.jwt.dto.TokenInfo;
import com.back.splitmeet.src.scheduling.dto.ScheduleInquiryInfo;
import com.back.splitmeet.src.scheduling.dto.ScheduleInquiryRes;

@Service
public class ScheduleService {

	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private ScheduleInfoRepository scheduleInfoRepository;

	public ScheduleService(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public ScheduleInquiryRes inquireSchedule(String accessToken) {
		TokenInfo tokenInfo = jwtTokenProvider.getUserInfoFromAcs(accessToken);
		UserInfo userInfo = userInfoRepository.findOneByUserId(tokenInfo.getUserId());

		if (userInfo.getUserTeam() == null)
			return new ScheduleInquiryRes(null, null, null, null);

		Long teamId = userInfo.getUserTeam().getTeamId();
		UserTeam userTeam = userInfo.getUserTeam();

		// 팀에 속한 모든 유저들을 불러옴
		Long numberOfMember = (long)userTeam.getUserInfo().size();
		List<Schedule> schedule = scheduleInfoRepository.findAllByTeamIdOrderByStartTime(teamId);

		if (schedule == null)
			return new ScheduleInquiryRes(teamId, userInfo.getUserTeam().getTeamName(), numberOfMember, null);

		List<ScheduleInquiryInfo> scheduleInquiryInfos = new ArrayList<>();

		for (Schedule schedule_temp : schedule) {
			ScheduleInquiryInfo scheduleInquiryInfo = new ScheduleInquiryInfo(
				schedule_temp.getScheduleId(),
				schedule_temp.getStartTime(),
				schedule_temp.getEndTime(),
				schedule_temp.getPlace(),
				schedule_temp.getCost());
			scheduleInquiryInfos.add(scheduleInquiryInfo);
		}

		ScheduleInquiryRes scheduleInquiryRes = new ScheduleInquiryRes(
			userInfo.getUserTeam().getTeamId(),
			userInfo.getUserTeam().getTeamName(),
			numberOfMember,
			scheduleInquiryInfos
		);
		return scheduleInquiryRes;
	}
}