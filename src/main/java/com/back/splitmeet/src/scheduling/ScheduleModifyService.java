package com.back.splitmeet.src.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.splitmeet.domain.Schedule;
import com.back.splitmeet.domain.repository.ScheduleInfoRepository;
import com.back.splitmeet.src.scheduling.dto.ScheduleModifyReq;
import com.back.splitmeet.src.scheduling.dto.ScheduleModifyRes;

@Service
public class ScheduleModifyService {
	@Autowired
	private ScheduleInfoRepository scheduleInfoRepository;

	public ScheduleModifyRes modifySchedule(ScheduleModifyReq req) {
		Schedule schedule = new Schedule();
		ScheduleModifyRes scheduleModifyRes = new ScheduleModifyRes();

		schedule.setTeamId(req.getTeamId());
		schedule.setDate(req.getDate());
		schedule.setPlace(req.getPlace());
		schedule.setCost(req.getCost());
		scheduleInfoRepository.save(schedule);

		scheduleModifyRes.setSuccess(1);
		return scheduleModifyRes;
	}
}
