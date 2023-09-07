package com.back.splitmeet.src.scheduling;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.Schedule;
import com.back.splitmeet.domain.repository.ScheduleInfoRepository;
import com.back.splitmeet.src.scheduling.dto.ScheduleCommunityReq;
import com.back.splitmeet.src.scheduling.dto.ScheduleCommunityRes;

@Service
public class ScheduleService {
	@Autowired
	private ScheduleInfoRepository scheduleInfoRepository;

	public List<ScheduleCommunityRes> inquireSchedule(ScheduleCommunityReq req) {
		List<Schedule> schedule = scheduleInfoRepository.findAllByTeamIdOrderByDate(req.getTeamId());
		ArrayList<ScheduleCommunityRes> scheduleCommunityRes = new ArrayList<>();

		for (Schedule schedule_temp : schedule) {
			scheduleCommunityRes.add(new ScheduleCommunityRes(
				schedule_temp.getDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
				schedule_temp.getDate().format(DateTimeFormatter.ofPattern("HH:mm")),
				schedule_temp.getPlace(),
				schedule_temp.getCost()
			));
		}
		return scheduleCommunityRes;
	}
}