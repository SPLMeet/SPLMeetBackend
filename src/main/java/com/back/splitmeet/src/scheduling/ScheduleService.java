package com.back.splitmeet.src.scheduling;

import com.back.splitmeet.domain.Schedule;
import com.back.splitmeet.domain.repository.ScheduleInfoRepository;
import com.back.splitmeet.domain.repository.UserInfoRepository;
import com.back.splitmeet.src.scheduling.dto.ScheduleCommunityReq;
import com.back.splitmeet.src.scheduling.dto.ScheduleCommunityRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleInfoRepository scheduleInfoRepository;

    public List<ScheduleCommunityRes> inquireSchedule(ScheduleCommunityReq req){
        List<Schedule> schedule = scheduleInfoRepository.findAllByTeamIdOrderByDate(req.getTeamId());
        List<ScheduleCommunityRes> scheduleCommunityRes = null;
        Schedule schedule_temp;

        for(int i=0;i<schedule.size();i++){
            schedule_temp = schedule.get(i);
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