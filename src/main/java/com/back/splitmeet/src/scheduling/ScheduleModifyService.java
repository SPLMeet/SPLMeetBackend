package com.back.splitmeet.src.scheduling;

import java.time.ZoneId;
import java.time.ZonedDateTime;

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

	private Schedule schedule = new Schedule();

	public ScheduleModifyRes modifySchedule(ScheduleModifyReq req) {
		ScheduleModifyRes scheduleModifyRes = new ScheduleModifyRes();
		ZonedDateTime dateAndTime = ZonedDateTime.of(
			Integer.parseInt(req.getDate().substring(0, 4)),
			Integer.parseInt(req.getDate().substring(4, 5)),
			Integer.parseInt(req.getDate().substring(5, 7)),
			Integer.parseInt(req.getDate().substring(0, 2)),
			Integer.parseInt(req.getDate().substring(2, 4)),
			0,
			0,
			ZoneId.of("Asia/Seoul")
		);

		schedule.setTeamId(req.getTeamId());
		schedule.setDate(dateAndTime);
		schedule.setPlace(req.getPlace());
		schedule.setCost(req.getCost());
		scheduleInfoRepository.save(schedule);

		scheduleModifyRes.setSuccess(1);
		System.out.println(scheduleModifyRes.getSuccess());
		return scheduleModifyRes;
	}

}
